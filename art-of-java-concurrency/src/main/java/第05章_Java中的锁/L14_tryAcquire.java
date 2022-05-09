package 第05章_Java中的锁;

/**
 * @author xxd
 * @description ReentrantLock的tryAcquire方法
 *
 * 该方法与nonfairTryAcquire(int acquires)比较，唯一不同的位置为判断条件多了 hasQueuedPredecessors()方法，
 * 即加入了同步队列中当前节点是否有前驱节点的判断，如果该 方法返回true，
 * 则表示有线程比当前线程更早地请求获取锁，因此需要等待前驱线程获取并释 放锁之后才能继续获取锁。
 * @date 2022-05-10
 */
public class L14_tryAcquire {
    //protected final boolean tryAcquire(int acquires) {
    //    final Thread current = Thread.currentThread();
    //    int c = getState();
    //    if (c == 0) {
    //        if (!hasQueuedPredecessors() && compareAndSetState(0, acquires)) {
    //            setExclusiveOwnerThread(current);
    //            return true;
    //        }
    //    } else if (current == getExclusiveOwnerThread()) {
    //        int nextc = c + acquires;
    //        if (nextc < 0) throw new Error("Maximum lock count exceeded");
    //        setState(nextc);
    //        return true;
    //    }
    //    return false;
    //}
}
