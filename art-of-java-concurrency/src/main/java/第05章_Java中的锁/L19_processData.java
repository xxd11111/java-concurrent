package 第05章_Java中的锁;

/**
 * @author xxd
 * @description 锁降级
 * <p>
 * 当数据发生变更后，update变量（布尔类型且volatile修饰）被设置为false，
 * 此时所有访问processData()方法的线程都能够感知到变化，但只有一个线程能够获取到写锁，其 他线程会被阻塞在读锁和写锁的lock()方法上。
 * 当前线程获取写锁完成数据准备之后，再获取 读锁，随后释放写锁，完成锁降级
 * @date 2022-05-10
 */
public class L19_processData {
    //public void processData() {
    //    readLock.lock();
    //    if (!update) {
    //        // 必须先释放读锁
    //        readLock.unlock();
    //        // 锁降级从写锁获取到开始
    //        writeLock.lock();
    //        try {
    //            if (!update) {
    //                // 准备数据的流程（略）
    //                update = true;
    //            }
    //            readLock.lock();
    //        } finally {
    //            writeLock.unlock();
    //        }
    //        // 锁降级完成，写锁降级为读锁
    //    }
    //    try {
    //        // 使用数据的流程（略）
    //    } finally {
    //        readLock.unlock();
    //    }
    //}
}