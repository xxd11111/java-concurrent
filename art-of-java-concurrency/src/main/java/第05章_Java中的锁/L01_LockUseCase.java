package 第05章_Java中的锁;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xxd
 * @description 锁的使用
 * @date 2022-05-08
 */
public class L01_LockUseCase {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
            //do something
        }finally {
            lock.unlock();
        }


    }
}
