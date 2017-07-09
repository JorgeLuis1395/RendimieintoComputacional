/*
 * To change this license header, choose License Headers inputThread Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template inputThread the editor.
 */
package servidor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author andres
 */
public class MultiThreadServer extends Thread {
    
    private Socket socket = null;
   
    public MultiThreadServer(Socket socket) 
    {
        super("MultiThreadServer");
        this.socket = socket;
    }
    
    @Override
    public void run()
    {
        try
        (
            BufferedReader inputThread = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outputThread = new PrintWriter(socket.getOutputStream(),true);
            Socket FE_clientSocket= new Socket("localhost", 5500);
//------------------------------------------------------------------------------
//Thread crea socket para comunicarse con el FE                
            BufferedReader inputclientSocket = new BufferedReader(new InputStreamReader(FE_clientSocket.getInputStream()));
            PrintWriter outputclientSocket = new PrintWriter(FE_clientSocket.getOutputStream(),true);
        )     
        {
            String  name, password,message,tothequeue, inputline;
            
            name=inputThread.readLine();
            password=inputThread.readLine();
            message=inputThread.readLine();
            tothequeue=name+"/"+password+"/"+message;
            
            outputclientSocket.println(tothequeue);

//Para segunda opción llegar hasta aqui-----------------------------------------            
            inputline=inputclientSocket.readLine();
            System.out.println(inputline);
            outputThread.println(inputline);
            
            FE_clientSocket.close();
            socket.close();
        } 
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
