package 第07章_取消与关闭;

import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @Description 反例,又一定概率无法结束，
 * @Author xxd
 * @Date 2021/10/18
 * @Version 1.0
 */
public class 不可靠的取消操作将生产者置于阻塞操作中 {
    static boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        consumePrimes();
    }

    static class BrokenPrimeProducer extends Thread {
        private final BlockingQueue<BigInteger> queue;
        private volatile boolean cancelled = false;

        BrokenPrimeProducer(BlockingQueue<BigInteger> queue) {
            this.queue = queue;
        }

        public void run() {
            try {
                BigInteger p = BigInteger.ONE;
                while (!cancelled){
                    System.out.println("put:"+p);
                    p = p.nextProbablePrime();
                    System.out.println("put:"+p);
                    //若queue.put（）方法阻塞时，调用cancel方法，依然会阻塞，无法结束任务
                    queue.put(p);
                    // Thread.sleep(1);
                }
            } catch (InterruptedException consumed) { }
        }
        public void cancel() { cancelled = true; }
    }

    static void consumePrimes() throws InterruptedException {
        BlockingQueue<BigInteger> primes = new ArrayBlockingQueue<>(10);
        BrokenPrimeProducer producer = new BrokenPrimeProducer(primes);
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
