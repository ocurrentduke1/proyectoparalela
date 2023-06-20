import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class implementacionChat extends UnicastRemoteObject implements chatServidor {

    public ArrayList<chatCliente> clientes;
    public ArrayList<Integer> tam;

    public implementacionChat() throws RemoteException{
        super();
        clientes = new ArrayList<chatCliente>();
        tam = new ArrayList<Integer>();
    }

    @Override
    public void registro(chatCliente cliente) throws RemoteException {
        this.clientes.add(cliente);
    }

    public void setTam(int num1) throws RemoteException {
        tam.add(num1);
    }

    public ArrayList<Integer> getTam() throws RemoteException {
        return this.tam;
    }

    @Override
    public void mensaje(String mensaje) throws RemoteException {
        int a = 0;
        while(a < clientes.size()){
            clientes.get(a++).mensajeCliente(mensaje);
        }
    }

}
