package 第05章_Java中的锁;

/**
 * @author xxd
 * @description 同步器的addWaiter和enq方法
 *
 * 代码通过使用compareAndSetTail(Node expect,Node update)方法来确保节点能够被线 程安全添加。
 * 试想一下：如果使用一个普通的LinkedList来维护节点之间的关系，那么当一个线程获取了同步状态，
 * 而其他多个线程由于调用tryAcquire(int arg)方法获取同步状态失败而并发地被添加到LinkedList时，
 * LinkedList将难以保证Node的正确添加，最终的结果可能是节点的数 量有偏差，而且顺序也是混乱的.
 *
 * 在enq(final Node node)方法中，同步器通过“死循环”来保证节点的正确添加，
 * 在“死循 环”中只有通过CAS将节点设置成为尾节点之后，当前线程才能从该方法返回，
 * 否则，当前线 程不断地尝试设置。可以看出，enq(final Node node)方法将并发添加节点的请求通过CAS变 得“串行化”了。
 * @date 2022-05-09
 */
public class L04_addWaiter {
    //private Node addWaiter(Node mode) {
    //    Node node = new Node(Thread.currentThread(), mode);
    //    // 快速尝试在尾部添加
    //    // Node pred = tail;
    //    if (pred != null) {
    //        node.prev = pred;
    //        if (compareAndSetTail(pred, node)) {
    //            pred.next = node;
    //            return node;
    //        }
    //    }
    //    enq(node);
    //    return node;
    //}
    //
    //private Node enq(final Node node) {
    //    for (; ; ) {
    //        Node t = tail;
    //        // Must initialize
    //        if (t == null) {
    //            if (compareAndSetHead(new Node())) tail = head;
    //        } else {
    //            node.prev = t;
    //            if (compareAndSetTail(t, node)) {
    //                t.next = node;
    //                return t;
    //            }
    //        }
    //    }
    //}
}
