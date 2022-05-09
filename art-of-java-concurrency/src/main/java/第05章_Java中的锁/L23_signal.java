package 第05章_Java中的锁;

/**
 * @author xxd
 * @description ConditionObject的signal方法
 * <p>
 * 调用该方法的前置条件是当前线程必须获取了锁，可以看到signal()方法进行了 isHeldExclusively()检查，也就是当前线程必须是获取了锁的线程。
 * 接着获取等待队列的首节点，将其移动到同步队列并使用LockSupport唤醒节点中的线程。
 *
 * 通过调用同步器的enq(Node node)方法，等待队列中的头节点线程安全地移动到同步队列。
 * 当节点移动到同步队列后，当前线程再使用LockSupport唤醒该节点的线程。
 * 被唤醒后的线程，将从await()方法中的while循环中退出（isOnSyncQueue(Node node)方法 返回true，节点已经在同步队列中），进而调用同步器的acquireQueued()方法加入到获取同步状 态的竞争中。
 * 成功获取同步状态（或者说锁）之后，被唤醒的线程将从先前调用的await()方法返回，此 时该线程已经成功地获取了锁。
 * Condition的signalAll()方法，相当于对等待队列中的每个节点均执行一次signal()方法，效 果就是将等待队列中所有节点全部移动到同步队列中，并唤醒每个节点的线程。
 * @date 2022-05-10
 */
public class L23_signal {
    //public final void signal() {
    //    if (!isHeldExclusively()) throw new IllegalMonitorStateException();
    //    Node first = firstWaiter;
    //    if (first != null) doSignal(first);
    //}
}
