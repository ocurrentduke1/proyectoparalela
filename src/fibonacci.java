import java.math.BigInteger;
class fibonacci {
    /**
     * Devuelve el numero fibonacci dado su posicion
     *
     * @param n Posicion del numero fibonacci
     * @return Numero de la lista de fibonacci
     */
    public static BigInteger fibonacciRecursivo(int n) {

        //CASO BASE, si es cero devuelve un cero
        if (n == 0) {
            return BigInteger.ZERO;
        }
        //CASO BASE, si es 1 devuelve un 1
        else if (n == 1) {
            return BigInteger.ONE;
        }
        //Hago la suma
        else {
            return fibonacciRecursivo(n - 1).add(fibonacciRecursivo(n - 2));
        }

    }
}



