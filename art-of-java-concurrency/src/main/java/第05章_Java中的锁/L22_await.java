package 第05章_Java中的锁;

import java.util.concurrent.locks.LockSupport;

/**
 * @author xxd
 * @description ConditionObject的await方法
 * 调用Condition的await()方法（或者以await开头的方法），会使当前线程进入等待队列并释 放锁，同时线程状态变为等待状态。当从await()方法返回时，当前线程一定获取了Condition相 关联的锁。
 *
 * 调用该方法的线程成功获取了锁的线程，也就是同步队列中的首节点，该方法会将当前 线程构造成节点并加入等待队列中，然后释放同步状态，唤醒同步队列中的后继节点，然后当 前线程会进入等待状态。
 * 当等待队列中的节点被唤醒，则唤醒节点的线程开始尝试获取同步状态。如果不是通过 其他线程调用Condition.signal()方法唤醒，而是对等待线程进行中断，则会抛出 InterruptedException。
 * @date 2022-05-10
 */
public class L22_await {
    //public final void await() throws InterruptedException {
    //    if (Thread.interrupted())
    //        throw new InterruptedException();
    //    // 当前线程加入等待队列
    //    Node node = addConditionWaiter();
    //    // 释放同步状态，也就是释放锁
    //    int savedState = fullyRelease(node);
    //    int interruptMode = 0;
    //    while (!isOnSyncQueue(node)) {
    //        LockSupport.park(this);
    //        if ((interruptMode = checkInterruptWhileWaiting(node)) != 0) break;
    //    }
    //    if (acquireQueued(node, savedState) && interruptMode != THROW_IE) interruptMode = REINTERRUPT;
    //    if (node.nextWaiter != null) unlinkCancelledWaiters();
    //    if (interruptMode != 0) reportInterruptAfterWait(interruptMode);
    //}
}
