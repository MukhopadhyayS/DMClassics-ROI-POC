package com.mckesson.eig.roi.utils;

//added file for veracode issue starts
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class AccessFileLoader {

    
    public static FileReader getFileReader(final String file) throws IOException {
        
        try {
            return (FileReader)AccessController.doPrivileged(new PrivilegedExceptionAction<Reader>() {
                public Reader run() throws IOException {
                    return new FileReader(file);
                }
            });
        } catch (PrivilegedActionException e) {
            throw (IOException) e.getCause();
        }
        
    }
    
    public static FileReader getFileReaderWithFile(final File file) throws IOException {
           
        try {
            return (FileReader)AccessController.doPrivileged(new PrivilegedExceptionAction<Reader>() {
                public Reader run() throws IOException {
                    return new FileReader(file);
                }
            });
        } catch (PrivilegedActionException e) {
            throw (IOException) e.getCause();
        }
        
    }
    
      public static File getFile(final String file) throws IOException {

          try {
              return (File) AccessController
                      .doPrivileged(new PrivilegedExceptionAction<File>() {
                          public File run() throws IOException {
                              return new File(file);
                          }
                      });
          } catch (PrivilegedActionException e) {
              throw (IOException) e.getCause();
          }

      }
      
      public static File getFile(final URI file) throws IOException {

          try {
              return (File) AccessController
                      .doPrivileged(new PrivilegedExceptionAction<File>() {
                          public File run() throws IOException {
                              return new File(file);
                          }
                      });
          } catch (PrivilegedActionException e) {
              throw (IOException) e.getCause();
          }

      }
      
      public static File getFile(final String file, final String secondFile) throws IOException {

          try {
              return (File) AccessController
                      .doPrivileged(new PrivilegedExceptionAction<File>() {
                          public File run() throws IOException {
                              return new File(file, secondFile);
                          }
                      });
          } catch (PrivilegedActionException e) {
              throw (IOException) e.getCause();
          }

      }
      
      public static FileInputStream getFileInputStream(final File file) throws IOException {
           
            try {
                return (FileInputStream)AccessController.doPrivileged(new PrivilegedExceptionAction<FileInputStream>() {
                    public FileInputStream run() throws IOException {
                        return new FileInputStream(file);
                    }
                });
            } catch (PrivilegedActionException e) {
                throw (IOException) e.getCause();
            }
            
        }
      
      public static FileInputStream getFileInputStream(final String file) throws IOException {
          
          try {
              return (FileInputStream)AccessController.doPrivileged(new PrivilegedExceptionAction<FileInputStream>() {
                  public FileInputStream run() throws IOException {
                      return new FileInputStream(file);
                  }
              });
          } catch (PrivilegedActionException e) {
              throw (IOException) e.getCause();
          }
          
      }
      
      public static FileOutputStream getFileOutputStream(final String file) throws IOException {
          
          try {
              return (FileOutputStream)AccessController.doPrivileged(new PrivilegedExceptionAction<FileOutputStream>() {
                  public FileOutputStream run() throws IOException {
                      return new FileOutputStream(file);
                  }
              });
          } catch (PrivilegedActionException e) {
              throw (IOException) e.getCause();
          }
          
      }
      
      public static FileOutputStream getFileOutputStream(final File file, final boolean flag) throws IOException {
          
          try {
              return (FileOutputStream)AccessController.doPrivileged(new PrivilegedExceptionAction<FileOutputStream>() {
                  public FileOutputStream run() throws IOException {
                      return new FileOutputStream(file, flag);
                  }
              });
          } catch (PrivilegedActionException e) {
              throw (IOException) e.getCause();
          }
          
      }
      
      public static FileOutputStream getFileOutputStream(final File file) throws IOException {
          
          try {
              return (FileOutputStream)AccessController.doPrivileged(new PrivilegedExceptionAction<FileOutputStream>() {
                  public FileOutputStream run() throws IOException {
                      return new FileOutputStream(file);
                  }
              });
          } catch (PrivilegedActionException e) {
              throw (IOException) e.getCause();
          }
          
      }
      
      public static FileOutputStream getFileOutputStream(final String file, final boolean flag) throws IOException {
          
          try {
              return (FileOutputStream)AccessController.doPrivileged(new PrivilegedExceptionAction<FileOutputStream>() {
                  public FileOutputStream run() throws IOException {
                      return new FileOutputStream(file, flag);
                  }
              });
          } catch (PrivilegedActionException e) {
              throw (IOException) e.getCause();
          }
          
      }
      
      public static InputStream getJarInputStream(final JarFile file, final JarEntry jf) throws IOException {
           
            try {
               
                return (InputStream)AccessController.doPrivileged(new PrivilegedExceptionAction<InputStream>() {
                    public InputStream run() throws IOException {
                        return file.getInputStream(jf);
                    }
                });
            } catch (PrivilegedActionException e) {
                throw (IOException) e.getCause();
            }
            
        }
      
      public static FileWriter getFileWriter(final File file) throws IOException {
          
          try {
              return (FileWriter)AccessController.doPrivileged(new PrivilegedExceptionAction<FileWriter>() {
                  public FileWriter run() throws IOException {
                      return new FileWriter(file);
                  }
              });
          } catch (PrivilegedActionException e) {
              throw (IOException) e.getCause();
          }
          
      }
      
      
      public static ProcessBuilder processBuild(final String[] cmd) throws IOException {

          try {
              return (ProcessBuilder) AccessController
                      .doPrivileged(new PrivilegedExceptionAction<ProcessBuilder>() {
                          public ProcessBuilder run() throws IOException {
                              return new ProcessBuilder(cmd);
                          }
                      });
          } catch (PrivilegedActionException e) {
              throw (IOException) e.getCause();
          }

      }
      
}
