package 第05章_Java中的锁;

/**
 * @author xxd
 * @description 成功获取锁的线程再次获取锁，只是增加了同步状态值，这也就要求ReentrantLock在释放同步状态时减少同步状态值
 *
 * 如果该锁被获取了n次，那么前(n-1)次tryRelease(int releases)方法必须返回false，而只有同 步状态完全释放了，才能返回true。
 * 可以看到，该方法将同步状态是否为0作为最终释放的条件，当同步状态为0时，将占有线程设置为null，并返回true，表示释放成功
 * @date 2022-05-10
 */
public class L13_tryRelease {
    //protected final boolean tryRelease(int releases) {
    //    int c = getState() - releases;
    //    if (Thread.currentThread() != getExclusiveOwnerThread()) throw new IllegalMonitorStateException();
    //    boolean free = false;
    //    if (c == 0) {
    //        free = true;
    //        setExclusiveOwnerThread(null);
    //    }
    //    setState(c);
    //    return free;
    //}
}
