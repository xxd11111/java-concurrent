package 第08章_Java中并发工具类;

import java.util.concurrent.CountDownLatch;

/**
 * @author xxd
 * @description
 *
 * CountDownLatch的构造函数接收一个int类型的参数作为计数器，如果你想等待N个点完 成，这里就传入N。
 * 当我们调用CountDownLatch的countDown方法时，N就会减1，CountDownLatch的await方法 会阻塞当前线程，直到N变成零。
 * 由于countDown方法可以用在任何地方，所以这里说的N个 点，可以是N个线程，也可以是1个线程里的N个执行步骤。
 * 用在多个线程时，只需要把这个 CountDownLatch的引用传递到线程里即可。
 *
 * 如果有某个解析sheet的线程处理得比较慢，我们不可能让主线程一直等待，所以可以使 用另外一个带指定时间的await方法——await（long time，TimeUnit unit），
 * 这个方法等待特定时 间后，就会不再阻塞当前线程。join也有类似的方法。
 *
 * 注意:计数器必须大于等于0，只是等于0时候，计数器就是零，调用await方法时不会 阻塞当前线程。
 * @date 2022-05-17
 */
public class L01_CountDownLatchTest {
    static CountDownLatch c = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(1);
                c.countDown();
                System.out.println(2);
                c.countDown();
            }
        }).start();
        c.await();
        System.out.println("3");
    }
}
