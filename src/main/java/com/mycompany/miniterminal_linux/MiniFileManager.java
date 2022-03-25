/*
* Clase MiniFileManager : Tendrá los atributos y métodos que necesites para realizar las distintas
* operaciones relacionadas con la gestión de archivos. Necesitarás al menos un método por cada
* operación. Se anzará una excepción si se produce un error o la operación solicitada no es posible.
* Algunos ejemplos que podrías implementar:
*   ● String getPWD() : Devuelve una cadena de texto con la carpeta actual.
*   ● boolean changeDir(String dir) : Cambia la carpeta actual a dir . Devuelve ‘true’ si fué posible.
*   ● void printList(boolean info) : Muestra una lista con los directorios y archivos de la carpeta
*     actual. Si info es ‘true’ mostrará también su tamaño y fecha de modificación.
*   ● etc.
*/
package com.mycompany.miniterminal_linux;

import java.io.File;
import java.util.Arrays;
import java.util.Date;

public class MiniFileManager {
    File ruta;
    
    public MiniFileManager(File ruta){
        this.ruta=ruta;
    }

    public String getPWD() {
        return ruta.getAbsolutePath();
    }
    
    public boolean changeDir(File dir){
        ruta=dir;
        return ruta.exists();
    }
    
    public void printList(boolean info){
        File arbolDir[]=ruta.listFiles();
        if(info==false){
            Arrays.sort(arbolDir);
            for(File s : arbolDir){
                if(s.isDirectory()){
                    System.out.println("[*] "+s.getName());
                }   
            }//Fin FOR Directories
            for(File s : arbolDir){
                if(s.isFile()){
                    System.out.println("[A] "+s.getName());
                }    
            }//Fin FOR Files
        }else{
            for(File s : arbolDir){
                if(s.isDirectory()){
                    System.out.println("[*] "+s.getName()+" --- Tamaño: "+s.length()+" --- Última modificación: "+new Date(s.lastModified()));
                }   
            }//Fin FOR Directories
            for(File s : arbolDir){
                if(s.isFile()){
                    System.out.println("[A] "+s.getName()+" --- Tamaño: "+s.length()+" --- Última modificación: "+new Date(s.lastModified()));
                }    
            }//Fin FOR Files
        }
    }
    
    public boolean makeDir(String dir){
        return (new File(dir)).mkdir();   
    }
    
    public boolean deleteDir(File dir){
        File contenido[]=dir.listFiles();
        if(dir.listFiles().length!=0){
            for(File f : contenido){
                if(f.isDirectory()){
                    System.out.println("No es posible borrar el directorio seleccionado ya que contiene otros directorios o subcarpetas en su interior.");
                    return false;
                }
            }
            for(File f : contenido){
                f.delete();
            }
        }        
        return dir.delete();   
    }
    
    public boolean moveFile(File origen, File destino){
        return origen.renameTo(destino);
    }
    
}//FIN Class
