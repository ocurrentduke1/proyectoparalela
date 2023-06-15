import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class servidorRMI {
    public static String HOST = "192.168.100.172";
    public static int PORT = 1099;

    public servidorRMI() {
    }

    public static void main(String[] var0) throws RemoteException {
        try {
            System.setProperty("java.rmi.server.hostname", HOST);
            Registry var1 = LocateRegistry.createRegistry(PORT);
            var1.rebind("Chat", new implementacionChat());
            System.out.println("Servidor Activo");
        } catch (RemoteException var2) {
        }

    }
}