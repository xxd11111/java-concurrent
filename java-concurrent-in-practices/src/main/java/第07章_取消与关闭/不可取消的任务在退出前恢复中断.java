package 第07章_取消与关闭;

import javafx.concurrent.Task;

import java.util.concurrent.BlockingQueue;

/**
 * @Description 不可取消的任务在退出前恢复中断并使用中断退出
 * @Author xxd
 * @Date 2021/10/18
 * @Version 1.0
 */
public class 不可取消的任务在退出前恢复中断 {
    public static void main(String[] args) throws InterruptedException {

    }

    public static Task getNextTask(BlockingQueue<Task> queue) {
        boolean interrupted = false;
        try {
            while (true) {
                try {
                    return queue.take();
                } catch (InterruptedException e) {
                    interrupted = true;
                }
            }
        } finally {
            if (interrupted)
                Thread.currentThread().interrupt();
        }
    }
}
