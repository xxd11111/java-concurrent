package 第07章_取消与关闭;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Description 不支持关闭的生产者消费者日志服务
 * @Author xxd
 * @Date 2021/10/19
 * @Version 1.0
 */
public class 不支持关闭的生产者消费者LogWriter日志服务 {
    public class LogWriter {
        private final BlockingQueue<String> queue;
        private final LoggerThread logger;
        private static final int CAPACITY = 1000;

        public LogWriter(Writer writer) {
            this.queue = new LinkedBlockingQueue<String>(CAPACITY);
            this.logger = new LoggerThread(writer);
        }

        public void start() {
            logger.start();
        }

        public void log(String msg) throws InterruptedException {
            queue.put(msg);
        }

        private class LoggerThread extends Thread {
            private final PrintWriter writer;

            public LoggerThread(Writer writer) {
                this.writer = new PrintWriter(writer, true); // autoflush
            }

            public void run() {
                try {
                    while (true)
                        writer.println(queue.take());
                } catch (InterruptedException ignored) {
                } finally {
                    writer.close();
                }
            }
        }

        //通过一种不可靠的方式为日志服务增加关闭支持，先判断后执行会有竞态危险
        // boolean shutdownRequested = false;
        // public void log(String msg) throws InterruptedException{
        //     if(! shutdownRequested)
        //         queue.put(msg);
        //     else
        //         throw new InterruptedException("logger is shut down");
        // }
    }
}
