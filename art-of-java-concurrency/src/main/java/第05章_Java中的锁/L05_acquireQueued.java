package 第05章_Java中的锁;

/**
 * @author xxd
 * @description 同步器的acquireQueued方法
 *
 * 节点进入同步队列之后，就进入了一个自旋的过程，每个节点（或者说每个线程）都在自 省地观察，
 * 当条件满足，获取到了同步状态，就可以从这个自旋过程中退出，否则依旧留在这 个自旋过程中（并会阻塞节点的线程）
 *
 * 在acquireQueued(final Node node,int arg)方法中，当前线程在“死循环”中尝试获取同步状 态，而只有前驱节点是头节点才能够尝试获取同步状态
 *
 * 原因有两个，如下。
 * 第一，头节点是成功获取到同步状态的节点，而头节点的线程释放了同步状态之后，将会 唤醒其后继节点，后继节点的线程被唤醒后需要检查自己的前驱节点是否是头节点。
 * 第二，维护同步队列的FIFO原则。该方法中，节点自旋获取同步状态
 *
 * 由于非首节点线程前驱节点出队或者被中断而从等待状态返回，随后检查自 己的前驱是否是头节点，如果是则尝试获取同步状态。
 * 可以看到节点和节点之间在循环检查 的过程中基本不相互通信，而是简单地判断自己的前驱是否为头节点，
 * 这样就使得节点的释 放规则符合FIFO，并且也便于对过早通知的处理（过早通知是指前驱节点不是头节点的线程 由于中断而被唤醒）。
 * @date 2022-05-09
 */
public class L05_acquireQueued {
    //final boolean acquireQueued(final Node node, int arg) {
    //    boolean failed = true;
    //    try {
    //        boolean interrupted = false;
    //        for (; ; ) {
    //            final Node p = node.predecessor();
    //            if (p == head && tryAcquire(arg)) {
    //                setHead(node);
    //                p.next = null; // help GC
    //                failed = false;
    //                return interrupted;
    //            }
    //            if (shouldParkAfterFailedAcquire(p, node) && parkAndCheckInterrupt()) interrupted = true;
    //        }
    //    } finally {
    //        if (failed) cancelAcquire(node);
    //    }
    //}
}
