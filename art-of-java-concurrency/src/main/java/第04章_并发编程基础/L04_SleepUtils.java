package 第04章_并发编程基础;

import java.util.concurrent.TimeUnit;

/**
 * @author xxd
 * @description 睡眠工具类
 * @date 2022-05-05
 */
public class L04_SleepUtils {
    public static void second(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException ignored) {
        }
    }
}
