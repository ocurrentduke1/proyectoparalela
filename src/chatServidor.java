import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface chatServidor extends Remote {
    void registro(chatCliente cliente) throws RemoteException;
    void mensaje(String mensaje)throws RemoteException;
    void setTam(int tam) throws RemoteException;
    ArrayList<Integer> getTam() throws RemoteException;
}
