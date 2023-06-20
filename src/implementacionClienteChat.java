import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.Remote;
import java.util.Scanner;


public class implementacionClienteChat extends UnicastRemoteObject implements chatCliente, Runnable{
    chatServidor servidor;
    public String nombre = null;

    implementacionClienteChat(String nombre, chatServidor servidor )throws RemoteException{
        this.nombre = nombre;
        this.servidor = servidor;
        servidor.registro(this);
    }

    @Override
    public void mensajeCliente(String mensaje) throws RemoteException {
        System.err.println(mensaje);
    }

    public void sendTam(int tam) throws RemoteException {
        System.out.println("El cliente definio el tamaño del array");
    }

    public int getTam() throws RemoteException {
        return servidor.getTam();
    }

    @Override
    public void run() {
        Scanner s = new Scanner(System.in);
        String mensaje;

        while(true){
            mensaje = s.nextLine();
            try {
                servidor.setTam(Integer.parseInt(mensaje));
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
