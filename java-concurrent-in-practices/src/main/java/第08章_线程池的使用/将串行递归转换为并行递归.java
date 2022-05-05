package 第08章_线程池的使用;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @Description 将串行递归转换为并行递归
 * @Author xxd
 * @Date 2021/10/21
 * @Version 1.0
 */
public class 将串行递归转换为并行递归 {

    public <T> void sequentialRecursive(List<Node<T>> nodes,
                                        Collection<T> results){
        for (Node<T> node : nodes) {
            results.add(node.compute());
            sequentialRecursive(node.getChildren(), results);
        }
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
