
package Colas;

public class Colas {
    public static final int DEFAULT_SIZE = 5;
    
    private String data[];
    private int index;

    public Colas() {
        this.data = new String[DEFAULT_SIZE];
    }
    
    public boolean isEmpty()
    {
        return index == 0;
    }
    
    public void agregarMensaje(String dato) throws Exception
    {
        if (index==DEFAULT_SIZE-1)
        {
            throw new Exception("Cola llena");
        }
        data[index]=dato;
        index++;
    }
    
    public String enviarMensaje() throws Exception
    {
        if (isEmpty())
        {
            throw new Exception("Cola vacia");
        }
        String aux=data[0];
        for (int i=0;i<index-1;i++)
        {
            data[i]=data[i+1];
        }
        index--;
        return aux;
    }
}
