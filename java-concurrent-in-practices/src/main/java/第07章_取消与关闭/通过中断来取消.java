package 第07章_取消与关闭;

import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @Description 使用线程自带的interrupt（）方法中断线程
 * @Author xxd
 * @Date 2021/10/18
 * @Version 1.0
 */
public class 通过中断来取消 {
    static boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        consumePrimes();
    }

    public static class PrimeProducer extends Thread{
        private final BlockingQueue<BigInteger> queue;
        PrimeProducer(BlockingQueue<BigInteger> queue) {
            this.queue = queue;
        }
        public void run() {
            try {
                BigInteger p = BigInteger.ONE;
                while (!Thread.currentThread().isInterrupted()){
                    p = p.nextProbablePrime();
                    System.out.println("put:"+p);
                    queue.put(p);
                }
            } catch (InterruptedException consumed) {
                /* Allow thread to exit */
            }
        }
        public void cancel() { interrupt(); }
    }

    static void consumePrimes() throws InterruptedException {
        BlockingQueue<BigInteger> primes = new ArrayBlockingQueue<>(10);
        PrimeProducer producer = new PrimeProducer(primes);
        producer.start();
        try {
            SECONDS.sleep(1);
            consume(primes.take());
        } finally {
            producer.cancel();
        }
    }

    private static boolean needMorePrimes() {
        return flag;
    }

    private static void consume(BigInteger take) {
        System.out.println("take:"+take);
    }
}
