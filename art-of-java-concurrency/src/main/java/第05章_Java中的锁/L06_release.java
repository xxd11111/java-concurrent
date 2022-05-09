package 第05章_Java中的锁;

/**
 * @author xxd
 * @description 同步器的release方法
 *
 * 当前线程获取同步状态并执行了相应逻辑之后，就需要释放同步状态，使得后续节点能 够继续获取同步状态。
 * 通过调用同步器的release(int arg)方法可以释放同步状态，该方法在释 放了同步状态之后，会唤醒其后继节点（进而使后继节点重新尝试获取同步状态）
 *
 * 该方法执行时，会唤醒头节点的后继节点线程，unparkSuccessor(Node node)方法使用 LockSupport（在后面的章节会专门介绍）来唤醒处于等待状态的线程。
 * @date 2022-05-09
 */
public class L06_release {
    //public final boolean release(int arg) {
    //    if (tryRelease(arg)) {
    //        Node h = head;
    //        if (h != null && h.waitStatus != 0) unparkSuccessor(h);
    //        return true;
    //    }
    //    return false;
    //}
}
