package 第05章_Java中的锁;

import 第04章_并发编程基础.L04_SleepUtils;

import java.util.concurrent.locks.Lock;

/**
 * @author xxd
 * @description 一个测试来验证TwinsLock是否能按照预期工作
 *
 * 在测试用例中，定义了工作者 线程Worker，该线程在执行过程中获取锁，当获取锁之后使当前线程睡眠1秒（并不释放锁）， 随后打印当前线程名称，最后再次睡眠1秒并释放锁
 *
 * 运行该测试用例，可以看到线程名称成对输出，也就是在同一时刻只有两个线程能够获 取到锁，这表明TwinsLock可以按照预期正确工作.
 * @date 2022-05-10
 */
public class L11_TwinsLockTest {
    public static void main(String[] args) {
        test();
    }

    public static void test() {
        final Lock lock = new L10_TwinsLock();
        class Worker extends Thread {
            public void run() {
                while (true) {
                    lock.lock();
                    try {
                        L04_SleepUtils.second(1);
                        System.out.println(Thread.currentThread().getName());
                        L04_SleepUtils.second(1);
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }
        // 启动10个线程
        for (int i = 0; i < 10; i++) {
            Worker w = new Worker();
            w.setDaemon(true);
            w.start();
        }
        // 每隔1秒换行
        for (int i = 0; i < 10; i++) {
            L04_SleepUtils.second(1);
            System.out.println();
        }
    }
}
