package 第05章_Java中的锁;

/**
 * @author xxd
 * @description ReentrantReadWriteLock的tryAcquire方法
 *
 * 写锁是一个支持重进入的排它锁。如果当前线程已经获取了写锁，则增加写状态。
 * 如果当 前线程在获取写锁时，读锁已经被获取（读状态不为0）或者该线程不是已经获取写锁的线程， 则当前线程进入等待状态
 *
 * 该方法除了重入条件（当前线程为获取了写锁的线程）之外，增加了一个读锁是否存在的判断。
 * 如果存在读锁，则写锁不能被获取，原因在于：读写锁要确保写锁的操作对读锁可见，如 果允许读锁在已被获取的情况下对写锁的获取，那
 * 么正在运行的其他读线程就无法感知到当 前写线程的操作。因此，只有等待其他读线程都释放了读锁，写锁才能被当前线程获取，而写 锁一旦被获取，则其他读写线程的后续访问均被阻塞。
 * 写锁的释放与ReentrantLock的释放过程基本类似，每次释放均减少写状态，当写状态为0 时表示写锁已被释放，从而等待的读写线程能够继续访问读写锁，同时前次写线程的修改对后续读写线程可见.
 * @date 2022-05-10
 */
public class L17_tryAcquire {
    //protected final boolean tryAcquire(int acquires) {
    //    Thread current = Thread.currentThread();
    //    int c = getState();
    //    int w = exclusiveCount(c);
    //    if (c != 0) {
    //        // 存在读锁或者当前获取线程不是已经获取写锁的线程
    //        if (w == 0 || current != getExclusiveOwnerThread()) return false;
    //        if (w + exclusiveCount(acquires) > MAX_COUNT) throw new Error("Maximum lock count exceeded");
    //        setState(c + acquires);
    //        return true;
    //    }
    //    if (writerShouldBlock() || !compareAndSetState(c, c + acquires)) {
    //        return false;
    //    }
    //    setExclusiveOwnerThread(current);
    //    return true;
    //}
}
