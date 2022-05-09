package 第05章_Java中的锁;

/**
 * @author xxd
 * @description 同步器的acquireShared和doAcquireShared方法
 *
 * 共享式访问资源时，其他共享式的访问均被允许，
 * 而独占式访问被阻塞，同一时刻其他访问均被阻塞。
 *
 * 在acquireShared(int arg)方法中，同步器调用tryAcquireShared(int arg)方法尝试获取同步状 态，tryAcquireShared(int arg)方法返回值为int类型，
 * 当返回值大于等于0时，表示能够获取到同 步状态。因此，在共享式获取的自旋过程中，成功获取到同步状态并退出自旋的条件就是 tryAcquireShared(int arg)方法返回值大于等于0。
 * 可以看到，在doAcquireShared(int arg)方法的自旋过程中，如果当前节点的前驱为头节点时，尝试获取同步状态，如果返回值大于等于0，表示 该次获取同步状态成功并从自旋过程中退出。
 * @date 2022-05-09
 */
public class L07_acquireShared_doAcquireShared {
    //public final void acquireShared(int arg) {
    //    if (tryAcquireShared(arg) < 0) doAcquireShared(arg);
    //}
    //
    //private void doAcquireShared(int arg) {
    //    final Node node = addWaiter(Node.SHARED);
    //    boolean failed = true;
    //    try {
    //        boolean interrupted = false;
    //        for (; ; ) {
    //            final Node p = node.predecessor();
    //            if (p == head) {
    //                int r = tryAcquireShared(arg);
    //                if (r >= 0) {
    //                    setHeadAndPropagate(node, r);
    //                    p.next = null;
    //                    if (interrupted) selfInterrupt();
    //                    failed = false;
    //                    return;
    //                }
    //            }
    //            if (shouldParkAfterFailedAcquire(p, node) && parkAndCheckInterrupt()) interrupted = true;
    //        }
    //    } finally {
    //        if (failed) cancelAcquire(node);
    //    }
    //}
}
