package 第07章_取消与关闭;

import net.jcip.annotations.GuardedBy;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;

/**
 * @Description 向logWriter添加可靠的取消操作
 * @Author xxd
 * @Date 2021/10/19
 * @Version 1.0
 */
public class 向LogWriter添加可靠的取消操作 {
    public class LogService {
        private final BlockingQueue<String> queue;
        private final LoggerThread loggerThread;
        private final PrintWriter writer;
        @GuardedBy("this")
        private boolean isShutdown;
        @GuardedBy("this")
        private int reservations;

        public LogService(BlockingQueue<String> queue, LoggerThread loggerThread, PrintWriter writer) {
            this.queue = queue;
            this.loggerThread = loggerThread;
            this.writer = writer;
        }

        public void start() {
            loggerThread.start();
        }

        public void stop() {
            synchronized (this) {
                isShutdown = true;
            }
            loggerThread.interrupt();
        }

        public void log(String msg) throws InterruptedException {
            synchronized (this) {
                if (isShutdown)
                    throw new IllegalStateException("logger is shut down");
                ++reservations;
            }
            queue.put(msg);
        }

        private class LoggerThread extends Thread {
            public void run() {
                try {
                    while (true) {
                        try {
                            synchronized (this) {
                                if (isShutdown && reservations == 0)
                                    break;
                            }
                            String msg = queue.take();
                            synchronized (this) {
                                --reservations;
                            }
                            writer.println(msg);
                        } catch (InterruptedException e) { /* retry */ }
                    }
                } finally {
                    writer.close();
                }
            }
        }
    }
}
