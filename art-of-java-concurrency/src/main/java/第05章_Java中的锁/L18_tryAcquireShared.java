package 第05章_Java中的锁;

/**
 * @author xxd
 * @description ReentrantReadWriteLock的tryAcquireShared方法
 * 读锁是一个支持重进入的共享锁，它能够被多个线程同时获取，在没有其他写线程访问 （或者写状态为0）时，读锁总会被成功地获取，
 * 而所做的也只是（线程安全的）增加读状态。如 果当前线程已经获取了读锁，则增加读状态。如果当前线程在获取读锁时，
 * 写锁已被其他线程 获取，则进入等待状态。获取读锁的实现从Java 5到Java 6变得复杂许多，主要原因是新增了一 些功能，例如getReadHoldCount()方法，
 * 作用是返回当前线程获取读锁的次数。读状态是所有线 程获取读锁次数的总和，而每个线程各自获取读锁的次数只能选择保存在ThreadLocal中，由 线程自身维护，
 * 这使获取读锁的实现变得复杂。因此，这里将获取读锁的代码做了删减，保留 必要的部分
 *
 * 在tryAcquireShared(int unused)方法中，如果其他线程已经获取了写锁，则当前线程获取读 锁失败，进入等待状态。
 * 如果当前线程获取了写锁或者写锁未被获取，则当前线程（线程安全， 依靠CAS保证）增加读状态，成功获取读锁。 读锁的每次释放（线程安全的，可能有多个读线程同时释放读锁）均减少读状态，减少的 值是（1<<16）。
 * @date 2022-05-10
 */
public class L18_tryAcquireShared {
    //protected final int tryAcquireShared(int unused) {
    //    for (; ; ) {
    //        int c = getState();
    //        int nextc = c + (1 << 16);
    //        if (nextc < c) throw new Error("Maximum lock count exceeded");
    //        if (exclusiveCount(c) != 0 && owner != Thread.currentThread()) return -1;
    //        if (compareAndSetState(c, nextc)) return 1;
    //    }
    //}
}
