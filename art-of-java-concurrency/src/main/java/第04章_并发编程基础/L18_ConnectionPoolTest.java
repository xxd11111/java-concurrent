package 第04章_并发编程基础;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xxd
 * @description 连接池测试
 *
 * 示例中使用了CountDownLatch来确保ConnectionRunnerThread能够同时开始执行，并且在全部结束之后，才使main线程从等待状态中返回。
 * 当前设定的场景是10个线程同时运行 获取连接池（10个连接）中的连接，通过调节线程数量来观察未获取到连接的情况。
 * 线程数、总获取次数、获取到的数量、未获取到的数量以及未获取到的比率，
 * 如表4-3所示（笔者机器CPU： i7-3635QM，内存为8GB，实际输出可能与此表不同）。
 *
 *  线程数量    总获取数    获取到次数   未取到次数   未获取到比率
 *  10          200         200         0             0%
 *  20          400         387        13          3.25%
 *  30          600         542        58          9.67%
 *  40          800         700       100          12.5%
 *  50          1000        828       172          17.2%
 * 从表中的数据统计可以看出，在资源一定的情况下（连接池中的10个连接），随着客户端线程的逐步增加，客户端出现超时无法获取连接的比率不断升高。
 * 虽然客户端线程在这种超 时获取的模式下会出现连接无法获取的情况，但是它能够保证客户端线程不会一直挂在连接获取的操作上，
 * 而是“按时”返回，并告知客户端连接获取出现问题，是系统的一种自我保护机制。
 * 数据库连接池的设计也可以复用到其他的资源获取的场景，针对昂贵资源（比如数据库连 接）的获取都应该加以超时限制。
 * @date 2022-05-07
 */
public class L18_ConnectionPoolTest {
    static L16_ConnectionPool pool = new L16_ConnectionPool(10);
    // 保证所有ConnectionRunner能够同时开始
    static CountDownLatch start = new CountDownLatch(1);
    // main线程将会等待所有ConnectionRunner结束后才能继续执行
    static CountDownLatch end;

    public static void main(String[] args) throws Exception {
        // 线程数量，可以修改线程数量进行观察
        int threadCount = 10;
        end = new CountDownLatch(threadCount);
        int count = 20;
        AtomicInteger got = new AtomicInteger();
        AtomicInteger notGot = new AtomicInteger();
        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(new ConnetionRunner(count, got, notGot), "ConnectionRunnerThread");
            thread.start();
        }
        start.countDown();
        end.await();
        System.out.println("total invoke: " + (threadCount * count));
        System.out.println("got connection: " + got);
        System.out.println("not got connection " + notGot);
    }

    static class ConnetionRunner implements Runnable {
        int count;
        AtomicInteger got;
        AtomicInteger notGot;

        public ConnetionRunner(int count, AtomicInteger got, AtomicInteger notGot) {
            this.count = count;
            this.got = got;
            this.notGot = notGot;
        }

        public void run() {
            try {
                start.await();
            } catch (Exception ex) {
            }
            while (count > 0) {
                try {
                    // 从线程池中获取连接，如果1000ms内无法获取到，将会返回null
                    // 分别统计连接获取的数量got和未获取到的数量notGot
                    Connection connection = pool.fetchConnection(1000);
                    if (connection != null) {
                        try {
                            connection.createStatement();
                            connection.commit();
                        } finally {
                            pool.releaseConnection(connection);
                            got.incrementAndGet();
                        }
                    } else {
                        notGot.incrementAndGet();
                    }
                } catch (Exception ex) {
                } finally {
                    count--;
                }
            }
            end.countDown();
        }
    }
}
