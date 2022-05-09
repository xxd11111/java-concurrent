package 第05章_Java中的锁;

/**
 * @author xxd
 * @description ReentrantLock是通过组合自定义同步器来实现锁的获取与释放，以非公平性（默认的）实 现为例，获取同步状态的代码
 *
 * 该方法增加了再次获取同步状态的处理逻辑：通过判断当前线程是否为获取锁的线程来 决定获取操作是否成功，如果是获取锁的线程再次请求，则将同步状态值进行增加并返回 true，表示获取同步状态成功。
 *
 * @date 2022-05-10
 */


public class L12_nonfairTryAcquire {
    //final boolean nonfairTryAcquire(int acquires) {
    //    final Thread current = Thread.currentThread();
    //    int c = getState();
    //    if (c == 0) {
    //        if (compareAndSetState(0, acquires)) {
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
