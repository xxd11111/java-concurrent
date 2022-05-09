package 第05章_Java中的锁;

/**
 * @author xxd
 * @description 通过调用同步器的acquire(int arg)方法可以获取同步状态，
 * 该方法对中断不敏感，也就是 由于线程获取同步状态失败后进入同步队列中，后续对线程进行中断操作时，线程不会从同 步队列中移出
 *
 * 代码主要完成了同步状态获取、节点构造、加入同步队列以及在同步队列中自旋等 待的相关工作，
 * 其主要逻辑是：首先调用自定义同步器实现的tryAcquire(int arg)方法，该方法 保证线程安全的获取同步状态，如果同步状态获取失败，
 * 则构造同步节点（独占式 Node.EXCLUSIVE，同一时刻只能有一个线程成功获取同步状态）并通过addWaiter(Node node) 方法将该节点加入到同步队列的尾部，
 * 最后调用acquireQueued(Node node,int arg)方法，使得该 节点以“死循环”的方式获取同步状态。如果获取不到则阻塞节点中的线程，而被阻塞线程的 唤醒主要依靠前驱节点的出队或阻塞线程被中断来实现。
 * @date 2022-05-09
 */
public class L03_acquire {
    //public final void acquire(int arg) {
    //    if (!tryAcquire(arg) && acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
    //        selfInterrupt();
    //}
}
