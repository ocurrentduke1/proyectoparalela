import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface chatCliente extends Remote{
    void mensajeCliente(String mensaje) throws RemoteException;
    ArrayList<Integer> getTam() throws RemoteException;
    void sendTam(int number) throws RemoteException;
}
