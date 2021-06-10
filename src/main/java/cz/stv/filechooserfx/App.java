package cz.stv.filechooserfx;


import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * JavaFX App
 */
public class App extends Application
{

  public static Scene scene;
  public Stage stage;
  private MainController mainController;
  boolean returnNull;
  
  @Override
  public void start(Stage stage) throws IOException
  {
    
    
    System.out.println(startFileChooser(stage , "").getAbsolutePath());
    
  }
  
  public File startFileChooser(Stage stage , String fileSuffix , String startDirectory , String bookmarksDir) throws IOException
  {
    showFileChooser(fileSuffix , startDirectory , bookmarksDir);
    return mainController.returnFile; 
  }
  
  public File startFileChooser(Stage stage , String fileSuffix , String startDirectory ) throws IOException
  {
    showFileChooser(fileSuffix , startDirectory , null);
    return mainController.returnFile; 
  }
  
  public File startFileChooser(Stage stage , String fileSuffix ) throws IOException
  {
    showFileChooser(fileSuffix , null , null);
    return mainController.returnFile; 
  }
  
  
  private void showFileChooser(String fileSuffix , String startDirectory , String bookmarksDir) throws IOException
  {
    Stage stage1 = new Stage();
    this . stage = stage1;
    scene = new Scene(loadFXML("main"));
    stage.setScene(scene);
    mainController.setApp(this);
    mainController.setSuffix(fileSuffix);
    if ( startDirectory != null )
      mainController.loadView(startDirectory);
    else
      mainController.loadView("/");
    
    
    if ( bookmarksDir != null )
      mainController.loadBookmarks(bookmarksDir); 
    
    stage.showAndWait();
  }

  private Parent loadFXML(String fxml) throws IOException
  {
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    Parent loaded = fxmlLoader.load();
    mainController = fxmlLoader.getController();
    return loaded;
  }
  
//==============================================================================
//getter , setter

  public void setSaveButtonText ( String text )
  {
    mainController.save.setText(text);
  }
  
  public void setCancelButtonText ( String text )
  {
    mainController.save.setText(text);
  }
  
  
//==============================================================================
  
  public static void main(String[] args)
  {
    launch();
  }

}
