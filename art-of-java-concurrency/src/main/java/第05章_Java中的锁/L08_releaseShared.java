package 第05章_Java中的锁;

/**
 * @author xxd
 * @description 同步器的releaseShared方法
 * <p>
 * 该方法在释放同步状态之后，将会唤醒后续处于等待状态的节点。对于能够支持多个线程同时访问的并发组件（比如Semaphore），它和独占式主要区别在于tryReleaseShared(int arg)
 * 方法必须确保同步状态（或者资源数）线程安全释放，一般是通过循环和CAS来保证的，因为 释放同步状态的操作会同时来自多个线程。
 * @date 2022-05-09
 */
public class L08_releaseShared {
    //public final boolean releaseShared(int arg) {
    //    if (tryReleaseShared(arg)) {
    //        doReleaseShared();
    //        return true;
    //    }
    //    return false;
    //}
}
