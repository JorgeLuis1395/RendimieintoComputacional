/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontBackEnd;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author andres
 */
public class enviar extends Thread{
    boolean heartbeat = true;
    Socket socket;
    PrintWriter writer;

    public enviar(Socket socket) throws IOException {
        this.socket = socket;
        this.writer = new PrintWriter(socket.getOutputStream(),true);
    }
    
    public void run ()
    {
        try
        {
            while(true)
            {
                if(heartbeat)
                {
                    Thread.sleep(10000);
                    System.out.println("estoy vivo");
                    writer.println("estoy vivo");
                }
            }
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
