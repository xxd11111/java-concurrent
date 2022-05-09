package 第05章_Java中的锁;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author xxd
 * @description 自定义同步组件
 *
 * TwinsLock实现了Lock接口，提供了面向使用者的接口，使用者调用lock() 方法获取锁，随后调用unlock()方法释放锁，而同一时刻只能有两个线程同时获取到锁。
 * TwinsLock同时包含了一个自定义同步器Sync，而该同步器面向线程访问和同步状态控制。以 共享式获取同步状态为例：同步器会先计算出获取后的同步状态，然后通过CAS确保状态的正确设置，
 * 当tryAcquireShared(int reduceCount)方法返回值大于等于0时，当前线程才获取同步状 态，对于上层的TwinsLock而言，则表示当前线程获得了锁。
 * 同步器作为一个桥梁，连接线程访问以及同步状态控制等底层技术与不同并发组件（比如 Lock、CountDownLatch等）的接口语义。
 * @date 2022-05-10
 */
public class L10_TwinsLock implements Lock {
    private final Sync sync = new Sync(2);

    private static final class Sync extends AbstractQueuedSynchronizer {
        Sync(int count) {
            if (count <= 0) {
                throw new IllegalArgumentException("count must large than zero.");
            }
            setState(count);
        }

        public int tryAcquireShared(int reduceCount) {
            for (; ; ) {
                int current = getState();
                int newCount = current - reduceCount;
                if (newCount < 0 || compareAndSetState(current, newCount)) {
                    return newCount;
                }
            }
        }

        public boolean tryReleaseShared(int returnCount) {
            for (; ; ) {
                int current = getState();
                int newCount = current + returnCount;
                if (compareAndSetState(current, newCount)) {
                    return true;
                }
            }
        }
    }

    public void lock() {
        sync.acquireShared(1);
    }

    public void unlock() {
        sync.releaseShared(1);
    }

    //以下未实现
    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
