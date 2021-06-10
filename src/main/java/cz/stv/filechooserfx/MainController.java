package cz.stv.filechooserfx;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;


public class MainController implements Initializable
{
  
  @FXML
  public Button save;
  
  @FXML
  public Button cancel;
  
  @FXML
  public ListView fileView;
  
  @FXML
  public ListView bookemarkView;
  
  @FXML
  public Button upArrrow;
  
  @FXML
  public TextField directory;
  
  @FXML
  public MenuButton menu;
  
  @FXML
  public MenuItem filter;
  
  @FXML
  public MenuItem all;
  
  @FXML
  public TextField saving;
  
  public File returnFile;
  
  public String path;
  private String suffix;
  
  private String suffixBackup;
  
  
  private FileChooser app;
  
  
  
  public void setApp(FileChooser app)
  {
    this.app = app;
  }
  
  
  
  
  @FXML
  public void upDirectory()
  {
    File f = new File(path);
    if ( f . getParentFile() != null )
      loadView(f.getParentFile().getAbsolutePath());
    
    saving.setText("");
  }
  
  public void loadView(String path)
  {
    this.path = path;
    ObservableList<FileView> list = fileView.getItems();
    list.clear();
    File f = new File(path);
    
    directory.setText(path);
    
    if (f.listFiles() != null)
    {
      for (File file : f.listFiles())
      {
        if ( file.isDirectory() )
          list.add(new FileView(file.getName() , file , file.getAbsolutePath()));
        else if ( file.isFile() )
        {
          
          if ( file.getName().endsWith(suffix) || suffix.equals("") )
            list.add(new FileView(file.getName() , file , file.getAbsolutePath()));
        }
        
      }
    }
    
    list.sort(new Comparator<FileView>()
    {
      @Override
      public int compare(FileView t , FileView t1)
      {
        if (t.getFile().isFile() && t1.getFile().isFile())
        {
          return t.toString().compareToIgnoreCase(t1.toString());
        }
        else if (t.getFile().isFile() && t1.getFile().isDirectory())
        {
          return 1;
        }
        else if (t1.getFile().isFile() && t.getFile().isDirectory())
        {
          return -1;
        }
        else
        {
          return t.toString().compareToIgnoreCase(t1.toString());
        }
      }
    });
  }
  
  public void loadBookmarks(String filePath) throws FileNotFoundException
  {
    ObservableList<FileView> list = bookemarkView.getItems();
    
    File gtkBookmarks = new File(filePath);
    Scanner scanner = new Scanner(gtkBookmarks);
    
    while (scanner.hasNextLine())
    {
      try
      {
        File f = new File(new URI(scanner.nextLine()));
        list.add(new FileView(f.getName() , f , f.getAbsolutePath()));
      }
      catch (URISyntaxException ex)
      {
        ex.printStackTrace(System.err);
      }
    }
  }
  
  @FXML
  public void mouseClickedOnBookmarkView(MouseEvent event)
  {
    if (event.getButton().equals(MouseButton.PRIMARY))
    {
      if (event.getClickCount() == 2)
      {
        FileView view = (FileView) bookemarkView.getSelectionModel().getSelectedItem();
        loadView(view.getAbsolutePath());
      }
    }
  }
  
  @FXML
  public void mouseClickedOnFileView(MouseEvent event)
  {
    if (event.getButton().equals(MouseButton.PRIMARY))
    {
      if (event.getClickCount() == 2)
      {
        FileView view = (FileView) fileView.getSelectionModel().getSelectedItem();
        if (view.getFile().isDirectory())
        {
          loadView(view.getAbsolutePath());
        }
      }
      else if ( event.getClickCount() == 1 )
      {
        FileView view = (FileView) fileView.getSelectionModel().getSelectedItem();
        if ( view.getFile().isFile()  )
        {
          saving . setText(view.toString());
        }
      }
    }
  }
  
  public void setSuffix(String suffix)
  {
    filter.setText(suffix);
    menu.setText("Suffix: " + suffix);
    suffixBackup = suffix;
    this.suffix = suffix;
  }
  
  @FXML
  public void dontFilter ( ActionEvent event )
  {
    menu.setText(all.getText());
    suffix="";
    loadView(path);
  }
  
  @FXML
  public void startFilter ( ActionEvent event )
  {
    suffix = suffixBackup;
    menu.setText("Suffix: " +suffix);
    loadView(path);
  }
  
  @FXML
  public void cancel(ActionEvent event)
  {
    returnFile = null;
    app.stage.close();
  }
  
  @FXML
  public void save ( ActionEvent event )
  {
    try
    {
      returnFile = new File(path+saving.getText());
      if (! returnFile.exists() )
        returnFile.createNewFile();
      app.stage.close();
    }
    catch (IOException ex)
    {
      ex.printStackTrace(System.err);
    }
  }
  
  @Override
  public void initialize(URL url , ResourceBundle rb)
  {
    directory.textProperty().addListener(this::textFormatter);
  }
  
  private void textFormatter(final ObservableValue<? extends String> ov , final String oldValue , final String newValue)
  {
    if ( newValue.lastIndexOf("/") == newValue.length() - 1
      &&  ! path.equals(directory.getText()))
      loadView(directory.getText());
  }
}
