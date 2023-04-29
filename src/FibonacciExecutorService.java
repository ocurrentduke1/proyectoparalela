import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class FibonacciExecutorService {

    private final int n;
    private final int threads;

    public FibonacciExecutorService(int n, int threads) {
        this.n = n;
        this.threads = threads;
    }

    public List<BigInteger> execute() {
        List<BigInteger> results = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        try {
            List<Future<BigInteger>> futures = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                FibonacciTask task = new FibonacciTask(i);
                futures.add(executor.submit(task));
            }
            for (Future<BigInteger> future : futures) {
                results.add(future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
        return results;
    }

    private static class FibonacciTask implements Callable<BigInteger> {

        private final int n;

        public FibonacciTask(int n) {
            this.n = n;
        }

        @Override
        public BigInteger call() {
            return fibonacci.fibonacciRecursivo(n);
        }
    }

}
