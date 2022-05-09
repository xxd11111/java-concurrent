package 第04章_并发编程基础;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * @author xxd
 * @description 它通过构造函数初始化连接的最大上限，通过一个双向队列 来维护连接，
 * 调用方需要先调用fetchConnection(long)方法来指定在多少毫秒内超时获取连接，
 * 当连接使用完成后，需要调用releaseConnection(Connection)方法将连接放回线程池
 * @date 2022-05-07
 */
public class L16_ConnectionPool {
    private LinkedList<Connection> pool = new LinkedList<>();

    //初始化 pool大小
    public L16_ConnectionPool(int initialSize) {
        if (initialSize > 0) {
            for (int i = 0; i < initialSize; i++) {
                pool.addLast(L17_ConnectionDriver.createConnection());
            }
        }
    }

    //释放连接  案例中等同于将连接加到pool中
    public void releaseConnection(Connection connection) {
        if (connection != null) {
            synchronized (pool) {
                // 连接释放后需要进行通知，这样其他消费者能够感知到连接池中已经归还了一个连接
                pool.addLast(connection);
                pool.notifyAll();
            }
        }
    }

    // 在mills内无法获取到连接，将会返回null
    public Connection fetchConnection(long mills) throws InterruptedException {
        synchronized (pool) {
            // 完全超时
            if (mills <= 0) {
                while (pool.isEmpty()) {
                    pool.wait();
                }
                return pool.removeFirst();
            } else {
                long future = System.currentTimeMillis() + mills;
                long remaining = mills;
                while (pool.isEmpty() && remaining > 0) {
                    pool.wait(remaining);
                    remaining = future - System.currentTimeMillis();
                }
                Connection result = null;
                if (!pool.isEmpty()) {
                    result = pool.removeFirst();
                }
                return result;
            }
        }
    }
}

