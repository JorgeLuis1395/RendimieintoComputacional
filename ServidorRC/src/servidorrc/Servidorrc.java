/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorrc;

import Servidor.ServidorHilos;
import java.io.IOException;
import java.net.ServerSocket;


/**
 *
 * @author andres
 */
public class Servidorrc {
    
    public static void main(String args[]) throws IOException
    {
        int portNumber = 4334;
        int x = 0;

//------------------------------------------------------------------------------
//Servidor levanta conexion
        try
        (
            ServerSocket serverSocket = new ServerSocket(portNumber);
        )        
                
        {   
            while( x<= 10)
            {     
//..............................................................................
//Cliente establece conexion con servidor, se genera hilo
                new ServidorHilos(serverSocket.accept()).start(); 
                x++;
                if(serverSocket.isClosed())
                    x--;           
            }
        }
        catch(IOException e)
        {
            System.err.println("No se pudo escuchar el puerto "+portNumber);
            System.exit(-1);
        }
//------------------------------------------------------------------------------        
    }
    
}

