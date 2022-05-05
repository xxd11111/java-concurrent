package 第04章_并发编程基础;

import java.util.concurrent.TimeUnit;

/**
 * @author xxd
 * @description 线程的状态
 *
 * new 初始状态，但是还没有调用start()
 * runnable 运行状态，java线程将操作系统中就绪和运行两种状态笼统的称作运行中
 * blocked  阻塞状态，表示线程阻塞于锁
 * waiting  等待状态，进入该状态表示当前线程需要其他线程做出一些特定动作，通知或中断
 * time_waiting 超时等待状态，不同于waiting，它可以在指定时间自行返回
 * terminated   终止状态，表示当前线程执行完毕
 *
 *
 * 运行该示例，打开终端或者命令提示符，键入“jps”，输出如下。
 *  611
 *  935 Jps
 *  929 ThreadState
 *  270
 *  可以看到运行示例对应的进程ID是929，接着再键入“jstack 929”（这里的进程ID需要和读者自己键入jps得出的ID一致），部分输出如下所示。
 *
 *   //BlockedThread-2线程阻塞在获取Blocked.class示例的锁上
 *   "BlockedThread-2" prio=5 tid=0x00007feacb05d000 nid=0x5d03 waiting for monitor entry [0x000000010fd58000] java.lang.Thread.State: BLOCKED (on object monitor)
 *   // BlockedThread-1线程获取到了Blocked.class的锁
 *   "BlockedThread-1" prio=5 tid=0x00007feacb05a000 nid=0x5b03 waiting on condition [0x000000010fc55000] java.lang.Thread.State: TIMED_WAITING (sleeping)
 *   // WaitingThread线程在Waiting实例上等待
 *   "WaitingThread" prio=5 tid=0x00007feacb059800 nid=0x5903 in Object.wait() [0x000000010fb52000] java.lang.Thread.State: WAITING (on object monitor)
 *   // TimeWaitingThread线程处于超时等待
 *   "TimeWaitingThread" prio=5 tid=0x00007feacb058800 nid=0x5703 waiting on condition [0x000000010fa4f000] java.lang.Thread.State: TIMED_WAITING (sleeping)
 *
 *  这是我的：
 * 11168 Launcher
 * 15684 L03_ThreadState
 * 1048 RemoteMavenServer36
 * 15688
 * 3064 Jps
 *
 * jstack 15684 部分结果：
 *
 * "BlockedThread-2" #16 prio=5 os_prio=0 tid=0x000000001e001800 nid=0x3574 waiting for monitor entry [0x000000001f0af000]
 *    java.lang.Thread.State: BLOCKED (on object monitor)
 *         at 第04章_并发编程基础.L03_ThreadState$Blocked.run(L03_ThreadState.java:50)
 *         - waiting to lock <0x000000076b9b77d0> (a java.lang.Class for 第04章_并发编程基础.L03_ThreadState$Blocked)
 *         at java.lang.Thread.run(Thread.java:748)
 *
 * "BlockedThread-1" #15 prio=5 os_prio=0 tid=0x000000001e001000 nid=0x2c78 waiting on condition [0x000000001efae000]
 *    java.lang.Thread.State: TIMED_WAITING (sleeping)
 *         at java.lang.Thread.sleep(Native Method)
 *         at java.lang.Thread.sleep(Thread.java:340)
 *         at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
 *         at 第04章_并发编程基础.L03_ThreadState$SleepUtils.second(L03_ThreadState.java:59)
 *         at 第04章_并发编程基础.L03_ThreadState$Blocked.run(L03_ThreadState.java:50)
 *         - locked <0x000000076b9b77d0> (a java.lang.Class for 第04章_并发编程基础.L03_ThreadState$Blocked)
 *         at java.lang.Thread.run(Thread.java:748)
 *
 * "WaitingThread" #14 prio=5 os_prio=0 tid=0x000000001dffe800 nid=0x3d30 in Object.wait() [0x000000001eeaf000]
 *    java.lang.Thread.State: WAITING (on object monitor)
 *         at java.lang.Object.wait(Native Method)
 *         - waiting on <0x000000076b9b4158> (a java.lang.Class for 第04章_并发编程基础.L03_ThreadState$Waiting)
 *         at java.lang.Object.wait(Object.java:502)
 *         at 第04章_并发编程基础.L03_ThreadState$Waiting.run(L03_ThreadState.java:36)
 *         - locked <0x000000076b9b4158> (a java.lang.Class for 第04章_并发编程基础.L03_ThreadState$Waiting)
 *         at java.lang.Thread.run(Thread.java:748)
 *
 * "TimeWaitingThread" #13 prio=5 os_prio=0 tid=0x000000001dfbc800 nid=0x1388 waiting on condition [0x000000001edae000]
 *    java.lang.Thread.State: TIMED_WAITING (sleeping)
 *         at java.lang.Thread.sleep(Native Method)
 *         at java.lang.Thread.sleep(Thread.java:340)
 *         at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
 *         at 第04章_并发编程基础.L03_ThreadState$SleepUtils.second(L03_ThreadState.java:59)
 *         at 第04章_并发编程基础.L03_ThreadState$TimeWaiting.run(L03_ThreadState.java:24)
 *         at java.lang.Thread.run(Thread.java:748)
 *
 * @date 2022-05-05
 */
public class L03_ThreadState {
    public static void main(String[] args) {
        new Thread(new TimeWaiting(), "TimeWaitingThread").start();
        new Thread(new Waiting(), "WaitingThread").start();
        // 使用两个Blocked线程，一个获取锁成功，另一个被阻塞
        new Thread(new Blocked(), "BlockedThread-1").start();
        new Thread(new Blocked(), "BlockedThread-2").start();
    }

    // 该线程不断地进行睡眠
    static class TimeWaiting implements Runnable {
        @Override
        public void run() {
            while (true) {
                L04_SleepUtils.second(100);
            }
        }
    }

    // 该线程在Waiting.class实例上等待
    static class Waiting implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (Waiting.class) {
                    try {
                        Waiting.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // 该线程在Blocked.class实例上加锁后，不会释放该锁
    static class Blocked implements Runnable {
        public void run() {
            synchronized (Blocked.class) {
                while (true) {
                    L04_SleepUtils.second(100);
                }
            }
        }
    }


}
