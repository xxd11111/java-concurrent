package 第08章_Java中并发工具类;

import java.util.concurrent.CyclicBarrier;

/**
 * @author xxd
 * @description
 * method1:
 * CyclicBarrier默认的构造方法是CyclicBarrier（int parties），
 * 其参数表示屏障拦截的线程数量，每个线程调用await方法告诉CyclicBarrier我已经到达了屏障，然后当前线程被阻塞。
 *
 *
 * method2:
 *  CyclicBarrier（int parties，Runnable barrierAction），用于每次在线程到达屏障时，优先执行barrierAction
 * 因为CyclicBarrier设置了拦截线程的数量是2，所以必须等代码中c.await()执行两次，再执行完线程A，都执行完之后，才会继续执行后续代码
 *
 * CyclicBarrier还提供其他有用的方法:
 * getNumberWaiting方法可以获得Cyclic-Barrier阻塞的线程数量。
 * isBroken()方法用来了解阻塞的线程是否被中断。
 * @date 2022-05-17
 */
public class L02_CyclicBarrierTest {
    public static void main(String[] args) {
        //method1();
        method2();
    }


    static CyclicBarrier c1 = new CyclicBarrier(2);
    public static void method1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    c1.await();
                } catch (Exception e) {
                }
                System.out.println(1);
            }
        }).start();

        try {
            c1.await();
        } catch (Exception e) {
        }
        System.out.println(2);
    }

    static CyclicBarrier c2 = new CyclicBarrier(2, new A());

    public static void method2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    c2.await();
                } catch (Exception e) {
                }
                System.out.println(1);
                try {
                    c2.await();
                } catch (Exception e) {
                }
            }
        }).start();
        try {
            c2.await();
            c2.await();
        } catch (Exception e) {
        }
        System.out.println(2);

    }

    static class A implements Runnable {
        @Override
        public void run() {
            System.out.println(3);
        }
    }
}
