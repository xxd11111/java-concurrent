package 第04章_并发编程基础;

import java.util.concurrent.TimeUnit;

/**
 * @author xxd
 * @description 线程中断
 * <p>
 * 中断可以理解为线程的一个标识位属性，它表示一个运行中的线程是否被其他线程进行 了中断操作。
 * <p>
 * 中断好比其他线程对该线程打了个招呼，其他线程通过调用该线程的interrupt() 方法对其进行中断操作。
 * 线程通过检查自身是否被中断来进行响应，线程通过方法isInterrupted()来进行判断是否 被中断，也可以调用静态方法Thread.interrupted()对当前线程的中断标识位进行复位。
 * 如果该 线程已经处于终结状态，即使该线程被中断过，在调用该线程对象的isInterrupted()时依旧会返 回false。
 * <p>
 * 从Java的API中可以看到，许多声明抛出InterruptedException的方法（例如Thread.sleep(long millis)方法）这些方法在抛出InterruptedException之前，Java虚拟机会先将该线程的中断标识位 清除，然后抛出InterruptedException，此时调用isInterrupted()方法将会返回false。
 *
 * 输出结果：
 * SleepThread interrupted is false
 * BusyThread interrupted is true
 *
 *  从结果可以看出，抛出InterruptedException的线程SleepThread，其中断标识位被清除了， 而一直忙碌运作的线程BusyThread，中断标识位没有被清除。
 *
 * @date 2022-05-05
 */
public class L07_Interrupted {
    public static void main(String[] args) throws Exception {
        // sleepThread不停的尝试睡眠
        Thread sleepThread = new Thread(new SleepRunner(), "SleepThread");
        sleepThread.setDaemon(true);
        // busyThread不停的运行
        Thread busyThread = new Thread(new BusyRunner(), "BusyThread");
        busyThread.setDaemon(true);
        sleepThread.start();
        busyThread.start();
        // 休眠5秒，让sleepThread和busyThread充分运行
        TimeUnit.SECONDS.sleep(5);
        sleepThread.interrupt();
        busyThread.interrupt();
        System.out.println("SleepThread interrupted is " + sleepThread.isInterrupted());
        System.out.println("BusyThread interrupted is " + busyThread.isInterrupted());
        // 防止sleepThread和busyThread立刻退出
        L04_SleepUtils.second(2);
    }

    static class SleepRunner implements Runnable {
        @Override
        public void run() {
            while (true) {
                L04_SleepUtils.second(10);
            }
        }
    }

    static class BusyRunner implements Runnable {
        @Override
        public void run() {
            while (true) {
            }
        }
    }
}
