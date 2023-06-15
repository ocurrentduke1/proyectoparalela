import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class interfaz extends JFrame{

    int tam = 0;
    BigInteger[] seq;
    static chatServidor servidor;

    public interfaz(){
        this.setBounds(150, 0, 1000, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        iniciarcomponentes();
        setLayout(null);
    }

    private void iniciarcomponentes() {
        JLabel lbsecuencial = new JLabel("tiempo secuencial");
        lbsecuencial.setBounds(200, 300, 200, 20);
        JLabel lbfork = new JLabel("tiempo forkjoin");
        lbfork.setBounds(450, 300, 200, 20);
        JLabel lbexecuter = new JLabel("tiempo executor");
        lbexecuter.setBounds(700, 300, 200, 20);
        JButton btndefinir = new JButton("definir");
        btndefinir.setBounds(150, 650, 100, 20);
        JButton btnsecuencial = new JButton("secuencial");
        btnsecuencial.setBounds(300, 650, 100, 20);
        JButton btnfork = new JButton("forkjoin");
        btnfork.setBounds(600, 650, 100, 20);
        JButton btnexecutor = new JButton("executor");
        btnexecutor.setBounds(450, 650, 100, 20);
        JButton btnlimpiar = new JButton("limpiar");
        btnlimpiar.setBounds(750, 650, 100, 20);
        JTextArea area1 = new JTextArea("");
        area1.setLineWrap(true);
        area1.setBounds(45, 30, 900, 200);
        area1.setBackground(Color.LIGHT_GRAY);
        JScrollPane scroll1 = new JScrollPane(area1);
        scroll1.setBounds(25, 30, 900, 200);
        scroll1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.getContentPane().add(lbsecuencial);
        this.getContentPane().add(lbfork);
        this.getContentPane().add(lbexecuter);
        this.getContentPane().add(btndefinir);
        this.getContentPane().add(btnsecuencial);
        this.getContentPane().add(btnexecutor);
        this.getContentPane().add(btnfork);
        this.getContentPane().add(btnlimpiar);
        this.getContentPane().add(scroll1);

        ActionListener definir = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                tam = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad de iteraciones para hacer"));
                    servidor.setTam(tam);
                } catch (RemoteException err) {
                    System.out.println(err);
                }
            }
        };

        btndefinir.addActionListener(definir);

        ActionListener secuencial = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long inicio = System.currentTimeMillis();
                try {
                    BigInteger[] seq = new BigInteger[servidor.getTam()];
                    for (int i = 0; i < servidor.getTam(); i++) {
                        seq[i] = fibonacci.fibonacciRecursivo(i);
                    }
                    area1.setText(Arrays.toString(seq));
                } catch (RemoteException error) {
                    System.out.println(error);
                }
                long fin = System.currentTimeMillis();
                long total = fin - inicio;
                lbsecuencial.setText("Tiempo secuencial: " + total + "ms");
            }
        };
        btnsecuencial.addActionListener(secuencial);


        ActionListener generarf = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ForkJoinPool pool = new ForkJoinPool(10);
                long inicio = System.currentTimeMillis();
                try {
                    BigInteger[] seq = new BigInteger[servidor.getTam()];
                    FibonacciFork task = new FibonacciFork(0, servidor.getTam());
                    for (int i = 0; i < servidor.getTam(); i++) {
                        seq[i] = pool.invoke(task);
                        task = new FibonacciFork(i + 1, servidor.getTam());
                    }
                    area1.setText(Arrays.toString(seq));
                }catch (RemoteException error){
                    System.out.println(error);
                }
                long fin = System.currentTimeMillis();
                long total = (fin - inicio);
                lbfork.setText("Tiempo forkjoin: " + total + "ms");
            }
        };
        btnfork.addActionListener(generarf);


        ActionListener generare = new ActionListener() {
            int threads = 10;
            @Override
            public void actionPerformed(ActionEvent e) {
                long inicio = System.currentTimeMillis();
                try {
                    FibonacciExecutorService executorService = new FibonacciExecutorService(servidor.getTam(), threads);
                    List<BigInteger> seq = executorService.execute();
                    area1.setText(Arrays.toString(seq.toArray()));
                }catch (RemoteException error){
                    System.out.println(error);
                }
                long fin = System.currentTimeMillis();
                long total = (fin - inicio);
                lbexecuter.setText("Tiempo Executor: " + total + "ms");
            }
        };
        btnexecutor.addActionListener(generare);


        ActionListener limpiar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                area1.setText("");
            }
        };
        btnlimpiar.addActionListener(limpiar);
    }

    public static void main(String[] args) {
        String nombre = JOptionPane.showInputDialog("Ingresa tu nombre");
        interfaz ventana = new interfaz();
        ventana.setVisible(true);
        try {
            Registry rmii = LocateRegistry.getRegistry(servidorRMI.HOST, servidorRMI.PORT);
            servidor = (chatServidor) rmii.lookup("Chat");
            new Thread(new implementacionClienteChat(nombre, servidor)).start();
        } catch (NotBoundException | RemoteException err) {
            System.out.println(err);
        }
    }
}
