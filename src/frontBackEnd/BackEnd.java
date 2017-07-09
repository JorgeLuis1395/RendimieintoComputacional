package frontBackEnd;
import frontBackEnd.ConexionBaseDatos;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import encriptar.encriptacion;

public class BackEnd extends Thread {
    private Socket cliente;
    private PrintWriter salida;
    private BufferedReader entrada;
    final int puerto=4314;
    final String HOST = "localhost";
    private String mensajeEntrada="",Usuario,nombre,mensajeRecibido;
    encriptacion encrip=new encriptacion();
    ConexionBaseDatos conecBaseDatos=new ConexionBaseDatos();
    public BackEnd(Socket cli){
        this.cliente=cli;
    }
    
    private boolean VerUsuario(){
        int i=0;
        String Usuario[]={"","",""},verificarNombre="";
        StringTokenizer tokens=new StringTokenizer(encrip.decepcriptar(mensajeEntrada), "\n");
        while(tokens.hasMoreTokens()){
            if(i>1)
                Usuario[i]+=tokens.nextToken();
            else{
                Usuario[i++]=tokens.nextToken();
            }
        }
        conecBaseDatos.Coneccion();
        verificarNombre=conecBaseDatos.SelectNombreContrasena(Usuario[0],Usuario[1]);
        conecBaseDatos.cerrarConeccion();
        this.Usuario=Usuario[0];
        if(!verificarNombre.equals("")){
            nombre=verificarNombre;
            return true;
        }else
            return false;
    }
     
    private void GuardarArchivo(String mensaje) throws IOException{
        BufferedWriter out = null;   
        try {   
            out = new BufferedWriter(new FileWriter("salida.txt", true));   
            out.write(mensaje);
            out.newLine();
            } catch (IOException e) {   
            // error processing code   
            } finally {   
                    if (out != null) {   
                out.close();   
            }      
        }
    }
    public void Ejecucion() throws IOException{
        try {
            cliente=new Socket(HOST,this.puerto);
            entrada=new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            salida=new PrintWriter(cliente.getOutputStream(), true);
             while(true){
                salida.println(entrada.toString());
                if((mensajeRecibido=entrada.readLine())!=null){
                    System.out.println("recibio el sms");
                    if(mensajeRecibido.equals("bye"))
                        cliente.close();
                }
                Thread.sleep(3000);
                System.out.println("deje de dormir");
            }
        } catch (IOException ex) {
            Logger.getLogger(BackEnd.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(BackEnd.class.getName()).log(Level.SEVERE, null, ex);
        }
            if((mensajeEntrada=entrada.readLine())!=null)
                if(VerUsuario())
                    salida.println(encrip.encriptar("Usuario : "+Usuario+" con nombre: "+nombre));
                else
                    salida.println(encrip.encriptar("Usuario : "+Usuario+" incorrecto"));
            cliente.close();
        }
    
    public void run(){
        try {
            Ejecucion();
        } catch (IOException ex) {
            Logger.getLogger(BackEnd.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String[] args) {
        try {
            final int puerto=4314;
            ServerSocket servidor=new ServerSocket(puerto);
            for(int i=0;i<100;i++){
                Socket cliente=new Socket();
                cliente=servidor.accept();
                BackEnd server=new BackEnd(cliente);
                server.start();
            }
            servidor.close();
        } catch (IOException ex) {
            Logger.getLogger(BackEnd.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
