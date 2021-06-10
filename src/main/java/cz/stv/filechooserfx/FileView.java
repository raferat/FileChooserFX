/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.stv.filechooserfx;


import java.io.File;


/**
 *
 * @author martin
 */
public class FileView
{
  private final String name;
  private final File file;
  private final String absolutePath;
  
  
  public FileView( String name , File file , String absolutePath)
  {
    this . name = name;
    this . file = file;
    this.absolutePath = absolutePath;
  }
  
  public File getFile()
  {
    return file;
  }
  
  public String getAbsolutePath ()
  {
    return absolutePath;
  }
  
  @Override
  public String toString ()
  {
    return name;
  }
}
