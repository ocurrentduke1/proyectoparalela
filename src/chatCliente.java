import java.rmi.RemoteException;
import java.rmi.Remote;

public interface chatCliente extends Remote{
    void mensajeCliente(String mensaje) throws RemoteException;
    int getTam() throws RemoteException;
    void sendTam(int number) throws RemoteException;
}
