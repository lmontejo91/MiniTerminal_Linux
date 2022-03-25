/*
 * Clase principal (con función main) que se encargará de interactuar con el
 * usuario e interpretar los comandos (qué comando se pide, argumentos, etc.). Utilizará la segunda
 * clase para realizar las operaciones de gestión de archivos. Manejará todas las posibles excepciones.
 */
package com.mycompany.miniterminal_linux;

import java.io.File;
import java.util.Scanner;

public class MiniTerminal {
    static Scanner teclado=new Scanner(System.in);
    static File carpeta_actual=new File("C:\\Users\\DAW\\PruebasMiniTerminal");
    static MiniFileManager ruta_actual=new MiniFileManager(carpeta_actual);
    
    public static void Menu(){
        String comando[], opcion;
        System.out.println("--- Mini Terminal LINUX ---"+
                           "● pwd : Muestra cual es la carpeta actual.\n" +
                           "● cd <DIR> : Cambia la carpeta actual a ‘DIR’. Con .. cambia a la carpeta superior.\n" +
                           "● ls : Muestra la lista de directorios y archivos de la carpeta actual (primero directorios, luego archivos, ambos ordenados alfabéticamente).\n" +
                           "● ll : Como ls pero muestra también el tamaño y la fecha de última modificación.\n" +
                           "● mkdir <DIR> : Crea el directorio ‘DIR’ en la carpeta actual.\n" +
                           "● rm <FILE> : Borra ‘FILE’. Si es una carpeta, borrará primero sus archivos y luego la carpeta. Si tiene subcarpetas, las dejará intactas y mostrará un aviso al usuario.\n" +
                           "● mv <FILE1> <FILE2> : Mueve o renombra ‘FILE1’ a ‘FILE2’.\n" +
                           "● help : Muestra una breve ayuda con los comandos disponibles.\n" +
                           "● exit : Termina el programa.");   
        
        opcion=teclado.nextLine();
        
        comando=opcion.split(" ");
        
        realizarComando(comando);      
    }
    
    public static void realizarComando(String comando[]){
        switch(comando[0]){
            case "pwd":
                System.out.println("Ruta actual: "+ruta_actual.getPWD());
            break;
            case "cd":
                if(comando.length==2){
                    ruta_actual.changeDir(new File(comando[1]));
                }else{
                    ruta_actual.changeDir(new File(comando[2]).getParentFile());
                }             
            break;
            case "ls":
                ruta_actual.printList(false);
            break;
            case "ll":
                ruta_actual.printList(true);
            break;
            case "mkdir":
                ruta_actual.makeDir(comando[1]);
            break;
            case "rm":
                File dirBorrar=new File(comando[1]);
                ruta_actual.deleteDir(dirBorrar);
            break;
            case "mv":
                ruta_actual.moveFile(new File(comando[1]), new File(comando[2]));
            break;
            case "help":
                              
            break;
            case "exit":
                              
            break;
            default:
                System.out.println("Comando introducido incorrecto. Inténtelo de nuevo.");
                Menu();
            break;
        }//FIN Switch;  
    }

    
    public static void main(String[] args) {
        Menu();
    }
}
