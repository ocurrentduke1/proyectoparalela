import java.math.BigInteger;
import java.util.concurrent.RecursiveTask;

public class FibonacciFork extends RecursiveTask<BigInteger> {
    private static final long serialVersionUID = 1L;
    private int n;
    private int tam;

    public FibonacciFork(int n, int tam) {
        this.n = n;
        this.tam = tam;
    }

    @Override
    protected BigInteger compute() {
        if (n <= 1) {
            return BigInteger.valueOf(n);
        }

        FibonacciFork f1 = new FibonacciFork(n - 1, tam);
        f1.fork();

        FibonacciFork f2 = new FibonacciFork(n - 2, tam);
        BigInteger res2 = f2.compute();

        BigInteger res1 = f1.join();

        return res1.add(res2);
    }
}