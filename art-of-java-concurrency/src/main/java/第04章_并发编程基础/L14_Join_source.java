package 第04章_并发编程基础;

/**
 * @author xxd
 * @description thread.join()源码（进行了部分调整）
 *
 * 当线程终止时，会调用线程自身的notifyAll()方法，会通知所有等待在该线程对象上的线程。
 * 可以看到join()方法的逻辑结构与等待/通知经典范式一致，即加锁、循环 和处理逻辑3个步骤。
 * @date 2022-05-06
 */
public class L14_Join_source {
    // 加锁当前线程对象
    //public final synchronized void join() throws InterruptedException {
    //    // 条件不满足，继续等待
    //    while (isAlive()) {
    //        wait(0);
    //    }
    //    // 条件符合，方法返回
    //}
}
