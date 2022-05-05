package 第08章_线程池的使用;

import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * @Description 等待通过并行方式计算的结果
 * @Author xxd
 * @Date 2021/10/21
 * @Version 1.0
 */
public class 等待通过并行方式计算的结果 {
    public<T> Collection<T> getParallelResults(List<Node<T>> nodes) throws InterruptedException{
        ExecutorService executorService = Executors.newCachedThreadPool();
        Queue<T> resultQueue = new ConcurrentLinkedDeque<>();
        parallelRecursive(executorService, nodes, resultQueue);
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        return resultQueue;
    }

    public <T> void parallelRecursive(final Executor executor,
                                      List<Node<T>> nodes,
                                      Collection<T> results){
        for (Node<T> node : nodes) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    results.add(node.compute());
                }
            });
            parallelRecursive(executor, node.getChildren(), results);
        }
    }
}
