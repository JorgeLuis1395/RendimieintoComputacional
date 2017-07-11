
package BackEnd;

import Conexion.ConexionBaseDatos;
import FrontEnd.EnviarHilo;
import encriptar.encriptacion;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import javax.net.ssl.SSLSocket;


public class BackEnd {

        public static void main(String[] args) throws IOException, InterruptedException {

        if (args.length != 2) {
            System.err.println(
                    "Use <host name> <port number>");
            System.exit(1);
        }
        
        
        String hostname=args[0];
        int portNumber = Integer.parseInt(args[1]);
        
        try 
        (

            Socket clientserver=new Socket(hostname, portNumber);
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientserver.getInputStream()));
            PrintWriter writer = new PrintWriter(clientserver.getOutputStream(),true);    
        ) 
        
        {                
            EnviarHilo sending = new EnviarHilo(clientserver);
            sending.start();
            
            String entrada, nombre="", contrasena="", mensaje="", respuesta;
            
            entrada=reader.readLine();
            
            StringTokenizer tokens = new StringTokenizer(entrada, "/");
            while(tokens.hasMoreTokens())
            {
                nombre=tokens.nextToken();
                contrasena= tokens.nextToken();
                mensaje= tokens.nextToken();
            }
            
           encriptacion encrip=new encriptacion();
            ConexionBaseDatos conecBaseDatos=new ConexionBaseDatos();
            conecBaseDatos.Coneccion();
       
            String validez = conecBaseDatos.SelectNombreContrasena(nombre, contrasena);
            
                encrip.encriptar(mensaje);
                      
            
                respuesta="lo sentimos su mensaje no pudo ser encriptado.";
            
            
            respuesta=nombre+"/"+contrasena+"/"+respuesta;
            writer.println(respuesta);
            
            clientserver.close();
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }

    }
}
