package 第05章_Java中的锁;

import 第04章_并发编程基础.L04_SleepUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TODO 测试用例有点小问题
 * @author xxd
 * @description 观察公平和非公平锁在获取锁时的区别
 * <p>
 * 在测试用例中定义了内部 类ReentrantLock2，该类主要公开了getQueuedThreads()方法，
 * 该方法返回正在等待获取锁的线 程列表，由于列表是逆序输出，为了方便观察结果，将其进行反转
 * <p>
 * 观察结果，公平性锁每次都是从同步队列中的 第一个节点获取到锁，而非公平性锁出现了一个线程连续获取锁的情况
 * @date 2022-05-10
 */
public class L15_FairAndUnFairTest {

    public static void main(String[] args) {
        //new L15_FairAndUnFairTest().fair();

        new L15_FairAndUnFairTest().unfair();
    }

    private static Lock fairLock = new ReentrantLock2(true);
    private static Lock unfairLock = new ReentrantLock2(false);

    public void fair() {
        testLock(fairLock);
    }

    public void unfair() {
        testLock(unfairLock);
    }

    private void testLock(Lock lock) {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        // 启动5个Job
        for (int i = 0; i < 5; i++) {
            Job job = new Job(lock, countDownLatch);
            job.start();
            countDownLatch.countDown();
        }
    }

    private static class Job extends Thread {
        private Lock lock;
        private CountDownLatch countDownLatch;

        public Job(Lock lock, CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
            this.lock = lock;
        }

        public void run() {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            lock.lock();
            L04_SleepUtils.second(2);
            try {
                // 连续2次打印当前的Thread和等待队列中的Thread（略）
                System.out.println("running:"+currentThread().getName());
                if (lock instanceof ReentrantLock2) {
                    ReentrantLock2 lock2 = (ReentrantLock2) lock;
                    lock2.printWait();
                    System.out.println();
                }
            } finally {
                lock.unlock();
            }

        }
    }

    private static class ReentrantLock2 extends ReentrantLock {
        public ReentrantLock2(boolean fair) {
            super(fair);
        }

        protected Collection<Thread> getQueuedThreads() {
            List<Thread> arrayList = new ArrayList<Thread>(super.getQueuedThreads());
            Collections.reverse(arrayList);
            return arrayList;
        }

        public void printWait() {
            Collection<Thread> queuedThreads = getQueuedThreads();
            queuedThreads.forEach(a -> System.out.println("wait:" + a.getName()));
        }
    }
}
