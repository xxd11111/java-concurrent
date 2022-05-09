package 第04章_并发编程基础;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author xxd
 * @description 等待，通知
 * <p>
 * 等待/通知机制，是指一个线程A调用了对象O的wait()方法进入等待状态，而另一个线程B 调用了对象O的notify()或者notifyAll()方法，线程A收到通知后从对象O的wait()方法返回，进而 执行后续操作。
 * 上述两个线程通过对象O来完成交互，而对象上的wait()和notify/notifyAll()的 关系就如同开关信号一样，用来完成等待方和通知方之间的交互工作。
 *
 * 所示代码中，创建了两个线程——WaitThread和NotifyThread，前者检查 flag值是否为false，如果符合要求，进行后续操作，否则在lock上等待，后者在睡眠了一段时间 后对lock进行通知.
 *
 * 输出结果：
 * Thread[WaitThread,5,main] flag is true. wait @ 22:03:07
 * Thread[NotifyThread,5,main] hold lock. notify @ 22:03:08
 * Thread[NotifyThread,5,main] hold lock again. sleep @ 22:03:13
 * Thread[WaitThread,5,main] flag is false. running @ 22:03:18
 *
 * 上述第3行和第4行输出的顺序可能会互换，而上述例子主要说明了调用wait()、notify()以 及notifyAll()时需要注意的细节，如下。
 *      1）使用wait()、notify()和notifyAll()时需要先对调用对象加锁。
 *      2）调用wait()方法后，线程状态由RUNNING变为WAITING，并将当前线程放置到对象的 等待队列。
 *      3）notify()或notifyAll()方法调用后，等待线程依旧不会从wait()返回，需要调用notify()或 notifAll()的线程释放锁之后，等待线程才有机会从wait()返回。
 *      4）notify()方法将等待队列中的一个等待线程从等待队列中移到同步队列中，而notifyAll() 方法则是将等待队列中所有的线程全部移到同步队列，被移动的线程状态由WAITING变为 BLOCKED。
 *      5）从wait()方法返回的前提是获得了调用对象的锁。
 *  从上述细节中可以看到，等待/通知机制依托于同步机制，其目的就是确保等待线程从 wait()方法返回时能够感知到通知线程对变量做出的修改。
 *
 *
 * @date 2022-05-06
 */
public class L11_WaitNotify {
    static boolean flag = true;
    static final Object lock = new Object();

    public static void main(String[] args) throws Exception {
        Thread waitThread = new Thread(new Wait(), "WaitThread");
        waitThread.start();
        TimeUnit.SECONDS.sleep(1);
        Thread notifyThread = new Thread(new Notify(), "NotifyThread");
        notifyThread.start();
    }

    static class Wait implements Runnable {
        public void run() {
            // 加锁，拥有lock的Monitor
            synchronized (lock) {
                // 当条件不满足时，继续wait，同时释放了lock的锁
                while (flag) {
                    try {
                        System.out.println(Thread.currentThread() + " flag is true. wait @ " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                        lock.wait();
                    } catch (InterruptedException e) {
                        //do something
                    }
                }
                // 条件满足时，完成工作
                System.out.println(Thread.currentThread() + " flag is false. running @ " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }
        }
    }

    static class Notify implements Runnable {
        public void run() {
            // 加锁，拥有lock的Monitor
            synchronized (lock) {
                // 获取lock的锁，然后进行通知，通知时不会释放lock的锁，
                // 直到当前线程释放了lock后，WaitThread才能从wait方法中返回
                System.out.println(Thread.currentThread() + " hold lock. notify @ " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                lock.notifyAll();
                flag = false;
                L04_SleepUtils.second(5);
            }
            // 再次加锁
            synchronized (lock) {
                System.out.println(Thread.currentThread() + " hold lock again. sleep @ " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                L04_SleepUtils.second(5);
            }
        }
    }
}
