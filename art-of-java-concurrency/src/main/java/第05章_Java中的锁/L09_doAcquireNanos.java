package 第05章_Java中的锁;

/**
 * @author xxd
 * @description 同步器的doAcquireNanos方法
 * <p>
 * 在Java 5之前，当一 个线程获取不到锁而被阻塞在synchronized之外时，对该线程进行中断操作，此时该线程的中 断标志位会被修改，但线程依旧会阻塞在synchronized上，等待着获取锁。
 * 在Java 5中，同步器 提供了acquireInterruptibly(int arg)方法，这个方法在等待获取同步状态时，如果当前线程被中 断，会立刻返回，并抛出InterruptedException。
 * <p>
 * 超时获取同步状态过程可以被视作响应中断获取同步状态过程的“增强版”， doAcquireNanos(int arg,long nanosTimeout)方法在支持响应中断的基础上，增加了超时获取的 特性。
 * 针对超时获取，主要需要计算出需要睡眠的时间间隔nanosTimeout，为了防止过早通知， nanosTimeout计算公式为：nanosTimeout-=now-lastTime，
 * 其中now为当前唤醒时间，lastTime为上 次唤醒时间，如果nanosTimeout大于0则表示超时时间未到，需要继续睡眠nanosTimeout纳秒， 反之，表示已经超时.
 * @date 2022-05-09
 */
public class L09_doAcquireNanos {
    //private boolean doAcquireNanos(int arg, long nanosTimeout) throws InterruptedException {
    //    long lastTime = System.nanoTime();
    //    final Node node = addWaiter(Node.EXCLUSIVE);
    //    boolean failed = true;
    //    try {
    //        for (; ; ) {
    //            final Node p = node.predecessor();
    //            if (p == head && tryAcquire(arg)) {
    //                setHead(node);
    //                p.next = null; // help GC
    //                failed = false;
    //                return true;
    //            }
    //            if (nanosTimeout <= 0) return false;
    //            if (shouldParkAfterFailedAcquire(p, node) && nanosTimeout > spinForTimeoutThreshold)
    //                LockSupport.parkNanos(this, nanosTimeout);
    //            long now = System.nanoTime();
    //            //计算时间，当前时间now减去睡眠之前的时间lastTime得到已经睡眠
    //            // 的时间delta，然后被原有超时时间nanosTimeout减去，得到了
    //            // 还应该睡眠的时间
    //            nanosTimeout -= now - lastTime;
    //            lastTime = now;
    //            if (Thread.interrupted()) throw new InterruptedException();
    //        }
    //    } finally {
    //        if (failed) cancelAcquire(node);
    //    }
    //}
}
