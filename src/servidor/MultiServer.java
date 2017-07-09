package servidor;

import java.io.IOException;
import java.net.ServerSocket;


/**
 *
 * @author andres
 */
public class MultiServer {
    
    public static void main(String args[]) throws IOException
    {
        if (args.length != 1)
        {
            System.err.println("Proveer numero puerto");
            System.exit(1);
        }
        
        int portNumber = Integer.parseInt(args[0]);
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
                new MultiThreadServer(serverSocket.accept()).start(); 
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

