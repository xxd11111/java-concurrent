package 第07章_取消与关闭;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @Description TODO
 * @Author xxd
 * @Date 2021/10/18
 * @Version 1.0
 */
public class 使用volatile类型的域来保存取消状态 {
    public static void main(String[] args) throws InterruptedException {
        List<BigInteger> bigIntegers = aSecondOfPrimes();
        System.out.println(bigIntegers);
    }

    //素数生成类
    public static class PrimeGenerator implements Runnable {
        private final List<BigInteger> primes
                = new ArrayList<>();
        private volatile boolean cancelled;

        public void run() {
            BigInteger bigInteger = BigInteger.ONE;
            while (!cancelled) {
                bigInteger = bigInteger.nextProbablePrime();
                synchronized (this) {
                    primes.add(bigInteger);
                }
            }
        }

        public void cancel() {
            cancelled = true;
        }

        public synchronized List<BigInteger> get() {
            return new ArrayList<BigInteger>(primes);
        }
    }

    // 一个只运行一秒钟的素数生成器
    static List<BigInteger> aSecondOfPrimes() throws InterruptedException {
        PrimeGenerator generator = new PrimeGenerator();
        new Thread(generator).start();
        try {
            SECONDS.sleep(1);
        } finally {
            generator.cancel();
        }
        return generator.get();
    }
}
