
package frontBackEnd;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class FrontEnd {
    
    public static void main (String args[])
    {
      
      if (args.length < 3) 
      {
        System.err.println("Usage: java EchoServer <port number>");
        System.exit(1);
      }
    
      int portNumberThread = Integer.parseInt(args[0]);
      String hostname = args[1];
      int portNumberBE = Integer.parseInt(args[2]);
      
      boolean listening = true;
      try 
      (        
        ServerSocket threadServerSocket = new ServerSocket(portNumberThread);
        Socket clientSocketBE = new Socket(hostname, portNumberBE);  
      )
      {
          String inputlineBE, inputlineTh;
          while (listening)
          {
//------------------------------------------------------------------------------
//Comunicacion con Thread 
              Socket threadClientSocket = threadServerSocket.accept();
              PrintWriter outTh = new PrintWriter(threadClientSocket.getOutputStream(),true);
              BufferedReader inTh = new BufferedReader(new InputStreamReader(threadClientSocket.getInputStream()));
              ColasSms colainput = new ColasSms();
              inputlineTh = inTh.readLine();
              colainput.agregarSms(inputlineTh);
//------------------------------------------------------------------------------              
//Comunicacion con Servidor BE
              
              leer reader =  new leer(clientSocketBE);
              reader.start();
              PrintWriter outBE = new PrintWriter(clientSocketBE.getOutputStream(),true);
              BufferedReader inBE = new BufferedReader(new InputStreamReader(clientSocketBE.getInputStream()));
              outBE.println(colainput.sacarSms());
              inputlineBE = inBE.readLine();
              outTh.println(inputlineBE);
//------------------------------------------------------------------------------              
          }
          clientSocketBE.close();
          threadServerSocket.close();
	} 
        
        catch (Exception e) 
        {
            e.getStackTrace();
       	}
    }
}
