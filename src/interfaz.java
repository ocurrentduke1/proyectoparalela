import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import javax.swing.*;

public class interfaz extends JFrame{

    long tam = 0;
    BigInteger[] seq;

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
        JButton btnsecuencial = new JButton("secuencial");
        btnsecuencial.setBounds(150, 650, 100, 20);
        JButton btnfork = new JButton("forkjoin");
        btnfork.setBounds(300, 650, 100, 20);
        JButton btnexecutor = new JButton("executor");
        btnexecutor.setBounds(600, 650, 100, 20);
        JButton btnlimpiar = new JButton("limpiar");
        btnlimpiar.setBounds(450, 650, 100, 20);
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
        this.getContentPane().add(btnsecuencial);
        this.getContentPane().add(btnexecutor);
        this.getContentPane().add(btnfork);
        this.getContentPane().add(btnlimpiar);
        this.getContentPane().add(scroll1);

        ActionListener secuencial = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tam = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de iteraciones"));
                long inicio = System.currentTimeMillis();
                BigInteger[] seq = new BigInteger[(int) tam];
                for (int i = 0; i < tam; i++) {
                    seq[i] = fibonacci.fibonacciRecursivo(i);
                }
                long fin = System.currentTimeMillis();
                long total = fin - inicio;
                area1.setText(Arrays.toString(seq));
                lbsecuencial.setText("Tiempo secuencial: " + total + "ms");
            }
        };
        btnsecuencial.addActionListener(secuencial);


        ActionListener generarf = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int tam = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de iteraciones"));
                ForkJoinPool pool = new ForkJoinPool(5);
                BigInteger[] seq = new BigInteger[tam];
                FibonacciFork task = new FibonacciFork(0, tam);
                long inicio = System.currentTimeMillis();
                for (int i = 0; i < tam; i++) {
                    seq[i] = pool.invoke(task);
                    task = new FibonacciFork(i+1, tam);
                }
                long fin = System.currentTimeMillis();
                long total = (fin - inicio);
                area1.setText(Arrays.toString(seq));
                lbfork.setText("Tiempo forkjoin: " + total + "ms");
            }
        };
        btnfork.addActionListener(generarf);


        ActionListener generare = new ActionListener() {
            int threads = 10;
            @Override
            public void actionPerformed(ActionEvent e) {
                int tam = Integer.parseInt(JOptionPane.showInputDialog("Ingresa el número de iteraciones"));
                FibonacciExecutorService executorService = new FibonacciExecutorService(tam, threads);
                long inicio = System.currentTimeMillis();
                List<BigInteger> seq = executorService.execute();
                long fin = System.currentTimeMillis();
                long total = (fin - inicio);
                area1.setText(Arrays.toString(seq.toArray()));
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
}
