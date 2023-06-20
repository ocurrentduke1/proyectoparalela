import java.math.BigInteger;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class implementacionChat extends UnicastRemoteObject implements chatServidor {

    public ArrayList<chatCliente> clientes;
    private int tam;

    public implementacionChat() throws RemoteException{
        clientes = new ArrayList<chatCliente>();
    }

    @Override
    public void registro(chatCliente cliente) throws RemoteException {
        this.clientes.add(cliente);
    }

    public void setTam(int tam) throws RemoteException {
        for (chatCliente client : clientes) {
            client.sendTam(tam);
            this.tam = tam;
        }
    }

    public int getTam() throws RemoteException {
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
