
package frontBackEnd;

public class ColasSms {
    public static final int DEFAULT_SIZE = 5;
    
    private String data[];
    private int index;

    public ColasSms() {
        this.data = new String[DEFAULT_SIZE];
    }
    
    public boolean isEmpty()
    {
        return index == 0;
    }
    
    public void agregarSms(String dato) throws Exception
    {
        if (index==DEFAULT_SIZE-1)
        {
            throw new Exception("Cola llena");
        }
        data[index]=dato;
        index++;
    }
    
    public String sacarSms() throws Exception
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
