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

    public File getRuta() {
        return ruta;
    }

    public String getPWD() {
        return ruta.getAbsolutePath();
    }
    
    /**
     * Cambia la ruta que almacena el objeto MiniFileManager.
     * 
     * @param dir Recibe objeto de tipo File.
     * @return Devuelve true si la ruta existe y false en caso contrario.
     * @throws FicheroNoEncontrado Si File dir no existe.
     * 
     * LÍNEA 45 --> IF que permite trabajar con rutas relativas. Permite introducir el nombre de un
     *              File que sea hijo directo del File al que apunta la ruta actual sin tener que 
     *              escribir la ruta completa, ya que este método se la añade.
     */
    public boolean changeDir(File dir) throws FicheroNoEncontrado{  
        if(!dir.toString().contains("\\")){
            dir=new File(ruta.getAbsolutePath()+"\\"+dir);            
        }        
        if(!dir.exists()){
          throw new FicheroNoEncontrado("Ruta al fichero especificado no existe. No se pudo cambiar el directorio activo.");
        }
        ruta=dir;
        return ruta.exists();
    }
    
    /**
     * Imprime listado de los directorios y ficheros contenidos en la ruta actual.
     * 
     * @param info Recibe boolean info. Si es 'false' imprime el listado solo con los nombres de 
     *             los archivos contenidos en la ruta actual. Si info es 'true' imprime también el 
     *             tamaño y fecha de última modificación de los mismos.
     */
    public void printList(boolean info){
        File arbolDir[]=ruta.listFiles();
        if(arbolDir.length==0){
            System.out.println("--- Directorio vacío ---");
        }
        if(info==false){                //ACCIONES PARA INFO=FALSE;
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
        }else{                          //ACCIONES PARA INFO=TRUE;
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
    
    /**
     * Crea un directorio en la ruta dada.
     * 
     * @param dir String conteniendo la ruta (relativa o absoluta) del nuevo directorio a crear.
     * @return Devuelve 'true' si el directorio ha sido creado correctamente. 'False' en caso contrario.
     * @throws FicheroNoEncontrado Si la ruta padre del directorio que se pretende crear no existe.
     */
    public boolean makeDir(String dir) throws FicheroNoEncontrado{
        if(dir.contains("\\")){    //ACCIONES SI dir CONTIENE RUTA ABSOLUTA;
            if(!((new File(dir)).getParentFile().exists())){
                throw new FicheroNoEncontrado("Ruta al fichero especificado no existe. Creación de directorio fallida.");
            }
            return (new File(dir)).mkdir();
        }else{                     //ACCIONES SI dir CONTIENE RUTA RELATIVA;
            return new File(ruta.getAbsolutePath()+"\\"+dir).mkdir();
        }
    }
    
    /**
     * Borra ficheros o directorios que no contengan subdirectorios.
     * 
     * @param dir Recibe objeto de tipo File.
     * @return Devuelve 'true' si el fichero o directorio ha sido borrado con éxito y 'false' en el
     *         caso de que el directorio contenga subdirectorios en su interior o el borrado no haya
     *         sido exitoso por cualquier otro motivo.
     * @throws FicheroNoEncontrado Si la ruta recibida para borrar no existe.
     */
    public boolean deleteDir(File dir) throws FicheroNoEncontrado{
        if(!(dir.toString().contains("\\"))){
           dir=new File(ruta.getAbsolutePath()+"\\"+dir);
        }
        if(!dir.exists()){
          throw new FicheroNoEncontrado("Ruta al fichero especificado no existe. No se pudo realizar la operación de borrar.");
        }
        File contenido[]=dir.listFiles();
        if(dir.listFiles().length!=0){
            for(File f : contenido){
                if(f.isDirectory()){
                    System.out.println("No es posible borrar el directorio seleccionado ya que contiene otros directorios o subcarpetas en su interior.");
                    return false;
                }
            }//Fin FOR Comprobación
            for(File f : contenido){
                f.delete();
            }//Fin FOR para Borrar
        }//Fin IF Directorio no vacío        
        return dir.delete();   
    }
        
    /**
     * Mueve o renombra un objeto de tipo File.
     * 
     * @param origen Objeto de tipo File que se desea renombrar o mover.
     * @param destino Objeto de tipo File que contiene el destino al que se desea mover o el nuevo nombre.
     * @return Devuelve 'true' si la operación de mover/renombrar se ha realizado con éxito. 'False' en caso contrario.
     * @throws FicheroNoEncontrado Si la ruta de origen o la ruta padre de la de destino no existen.
     */
    public boolean moveFile(File origen, File destino) throws FicheroNoEncontrado{
        if(!(origen.toString().contains("\\"))){
            origen=new File(ruta.getAbsolutePath()+"\\"+origen);
        }
        if(!(destino.toString().contains("\\"))){
            destino=new File(ruta.getAbsolutePath()+"\\"+destino);
        }
        /*
        Los anteriores dos IF´s permiten operar con rutas relativas. Si las rutas de los File 
        recibidos no contienen '\' entonces suponemos que trabajamos sobre el directorio actual 
        y se transforman a ruta absoluta.
        */
        
        if(!origen.exists() || !destino.getParentFile().exists()){
          throw new FicheroNoEncontrado("Ruta al fichero especificado no existe. No se pudo mover o renombrar el directorio.");
        }
        
        return origen.renameTo(destino);
    }
    
}//FIN Class
