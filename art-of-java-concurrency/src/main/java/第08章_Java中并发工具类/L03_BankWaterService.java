package 第08章_Java中并发工具类;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @author xxd
 * @description CyclicBarrier的应用场景
 * <p>
 * CyclicBarrier可以用于多线程计算数据，最后合并计算结果的场景。例如，用一个Excel保 存了用户所有银行流水，每个Sheet保存一个账户近一年的每笔银行流水，
 * 现在需要统计用户 的日均银行流水，先用多线程处理每个sheet里的银行流水，都执行完之后，得到每个sheet的日 均银行流水，
 * 最后，再用barrierAction用这些线程的计算结果，计算出整个Excel的日均银行流水.
 *
 * 使用线程池创建4个线程，分别计算每个sheet里的数据，每个sheet计算结果是1，再由 BankWaterService线程汇总4个sheet计算出的结果.
 *
 * @date 2022-05-17
 */
public class L03_BankWaterService implements Runnable{
    /*** 创建4个屏障，处理完之后执行当前类的run方法 */
    private CyclicBarrier c = new CyclicBarrier(4, this);
    /*** 假设只有4个sheet，所以只启动4个线程 */
    private Executor executor = Executors.newFixedThreadPool(4);
    /*** 保存每个sheet计算出的银流结果 */
    private ConcurrentHashMap<String, Integer> sheetBankWaterCount = new ConcurrentHashMap<String, Integer>();

    private void count() {
        for (int i = 0; i < 4; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    // 计算当前sheet的银流数据，计算代码省略
                    sheetBankWaterCount.put(Thread.currentThread().getName(), 1);
                    // 银流计算完成，插入一个屏障
                    try {
                        c.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    @Override
    public void run() {
        int result = 0;
        // 汇总每个sheet计算出的结果
        for (Map.Entry<String, Integer> sheet : sheetBankWaterCount.entrySet()) {
            result += sheet.getValue();
        }
        // 将结果输出
        sheetBankWaterCount.put("result", result);
        System.out.println(result);
    }

    public static void main(String[] args) {
        L03_BankWaterService bankWaterCount = new L03_BankWaterService();
        bankWaterCount.count();
    }
}
