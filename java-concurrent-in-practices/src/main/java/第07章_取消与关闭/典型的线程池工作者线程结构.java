package 第07章_取消与关闭;

import java.util.concurrent.BlockingQueue;

/**
 * @Description 典型的线程池工作者线程结构
 * 存在争议，当线程抛出未检查异常时，整个应用都可能受到影响，但其替代方法--关闭整个应用程序，通常更不切实际
 * @Author xxd
 * @Date 2021/10/19
 * @Version 1.0
 */
public class 典型的线程池工作者线程结构 {
    public class WorkerThread extends Thread {
        private final BlockingQueue<Runnable> queue;

        public WorkerThread(BlockingQueue<Runnable> queue) {
            this.queue = queue;
        }

        public void run() {
            while (true) {
                try {
                    Runnable task = queue.take();
                    task.run();
                } catch (InterruptedException e) {
                    break; /* Allow thread to exit */
                }
            }
        }
    }
}
