
package FrontEnd;

import Colas.Colas;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class FrontEnd {
    
    public static void main (String args[])
    {
      
      if (args.length < 2) 
      {
        System.err.println("Usage: java EchoServer <port number>");
        System.exit(1);
      }
    
      int portNumberThread = Integer.parseInt(args[0]);
     
      int portNumberBE = Integer.parseInt(args[1]);
      
      boolean listening = true;
      try 
      (        
        ServerSocket threadServerSocket = new ServerSocket(portNumberThread);
        ServerSocket clientSocketBE = new ServerSocket(portNumberBE);  
      )
      {
          String inputlineBE, inputlineTh;
          while (listening)
          {
             Socket threadClientSocket = threadServerSocket.accept();
              PrintWriter outTh = new PrintWriter(threadClientSocket.getOutputStream(),true);
              BufferedReader inTh = new BufferedReader(new InputStreamReader(threadClientSocket.getInputStream()));
              Colas colainput = new Colas();
              inputlineTh = inTh.readLine();
              colainput.agregarMensaje(inputlineTh);
           Socket BEsocket=clientSocketBE.accept();
                
                PrintWriter outBE = new PrintWriter(BEsocket.getOutputStream(),true);
                BufferedReader inBE = new BufferedReader(new InputStreamReader(BEsocket.getInputStream()));
                System.out.println(inBE.readLine());
                
                outBE.println(colainput.enviarMensaje());
                inputlineBE = inBE.readLine();
                outTh.println(inputlineBE);
                

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
