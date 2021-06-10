package cz.stv.filechooserfx;


import java.io.File;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * JavaFX FileChooser
 * 
 * @author Raferat
 * @version 1.0.1
 */
public class FileChooser
{

  static Scene scene;
  Stage stage;
  MainController mainController;
  
  
  /**
   * startFileChooser starts the FileChooser
   * 
   * 
   * @param fileSuffix suffix of the file or empty string if you don't want to
   * @param startDirectory directory where should the FileChooser start
   * @param bookmarksDir where are located bookmarks
   * @return selected file or null if canceled button was pressed
   * @throws IOException 
   */  
  public File startFileChooser(String fileSuffix , String startDirectory , String bookmarksDir) throws IOException
  {
    showFileChooser(fileSuffix , startDirectory , bookmarksDir);
    return mainController.returnFile; 
  }
  
  /**
   * startFileChooser starts the FileChooser
   * 
   * 
   * @param fileSuffix suffix of the file or empty string if you don't want to
   * @param startDirectory directory where should the FileChooser start
   * @return selected fileor null if canceled button was pressed
   * @throws IOException 
   */  
  public File startFileChooser(String fileSuffix , String startDirectory ) throws IOException
  {
    showFileChooser(fileSuffix , startDirectory , null);
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
      throw new NullPointerException("startDirectory is null");
    
    if ( bookmarksDir != null )
      mainController.loadBookmarks(bookmarksDir); 
    
    stage.showAndWait();
  }

  private Parent loadFXML(String fxml) throws IOException
  {
    FXMLLoader fxmlLoader = new FXMLLoader(FileChooser.class.getResource(fxml + ".fxml"));
    Parent loaded = fxmlLoader.load();
    mainController = fxmlLoader.getController();
    return loaded;
  }
  
//==============================================================================
//getter , setter

  /**
   * Sets the text of save button
   * @param text is setted as button text
   */
  public void setSaveButtonText ( String text )
  {
    mainController.save.setText(text);
  }
  
  /**
   * Sets the text of cancel button
   * @param text is setted as button text
   */
  public void setCancelButtonText ( String text )
  {
    mainController.save.setText(text);
  }
  
  
//==============================================================================
}
