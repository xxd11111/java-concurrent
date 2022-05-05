package 第07章_取消与关闭;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @Description 通过newTaskFor将非标准的取消操作封装在一个任务中
 * @Author xxd
 * @Date 2021/10/18
 * @Version 1.0
 */
public class 通过newTaskFor将非标准的取消操作封装在一个任务中 {

    interface CancellableTask <T> extends Callable<T> {
        void cancel();

        RunnableFuture<T> newTask();
    }


    public abstract class SocketUsingTask <T> implements CancellableTask<T> {
        @GuardedBy("this") private Socket socket;

        protected synchronized void setSocket(Socket s) {
            socket = s;
        }

        public synchronized void cancel() {
            try {
                if (socket != null)
                    socket.close();
            } catch (IOException ignored) {
            }
        }

        public RunnableFuture<T> newTask() {
            return new FutureTask<T>(this) {
                public boolean cancel(boolean mayInterruptIfRunning) {
                    try {
                        SocketUsingTask.this.cancel();
                    } finally {
                        return super.cancel(mayInterruptIfRunning);
                    }
                }
            };
        }
    }


    @ThreadSafe
    class CancellingExecutor extends ThreadPoolExecutor {
        public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        }

        public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        }

        public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
        }

        public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        }

        protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
            if (callable instanceof CancellableTask)
                return ((CancellableTask<T>) callable).newTask();
            else
                return super.newTaskFor(callable);
        }
    }
}
