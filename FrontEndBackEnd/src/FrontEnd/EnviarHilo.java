package FrontEnd;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class EnviarHilo extends Thread{
    boolean heartbeat = true;
    Socket socket;
    PrintWriter writer;

    public EnviarHilo(Socket socket) throws IOException {
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
