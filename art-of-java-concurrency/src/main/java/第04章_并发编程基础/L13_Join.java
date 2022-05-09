package 第04章_并发编程基础;

import java.util.concurrent.TimeUnit;

/**
 * @author xxd
 * @description Thread.join()的使用
 * <p>
 * 在代码L_13所示的例子中，创建了10个线程，编号0~9，每个线程调用前一个线程的 join()方法，
 * 也就是线程0结束了，线程1才能从join()方法中返回，而线程0需要等待main线程结束。
 *
 * 输出结果：
 *
 * 从上述输出可以看到，每个线程终止的前提是前驱线程的终止，每个线程等待前驱线程 终止后，才从join()方法返回，这里涉及了等待/通知机制（等待前驱线程结束，接收前驱线程结 束通知）。
 * @date 2022-05-06
 */
public class L13_Join {
    public static void main(String[] args) throws Exception {
        Thread previous = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            // 每个线程拥有前一个线程的引用，需要等待前一个线程终止，才能从等待中返回
            Thread thread = new Thread(new Domino(previous), String.valueOf(i));
            thread.start();
            previous = thread;
        }
        TimeUnit.SECONDS.sleep(5);
        System.out.println(Thread.currentThread().getName() + " terminate.");
    }

    static class Domino implements Runnable {
        private Thread thread;

        public Domino(Thread thread) {
            this.thread = thread;
        }

        public void run() {
            try {
                thread.join();
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread().getName() + " terminate.");
        }
    }
}
