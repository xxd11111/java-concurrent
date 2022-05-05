package 第07章_取消与关闭;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @Description 将异常写入日志的UncaughtExceptionHandler
 * @Author xxd
 * @Date 2021/10/19
 * @Version 1.0
 */
public class 将异常写入日志的UncaughtExceptionHandler {
    public class UEHLogger implements Thread.UncaughtExceptionHandler {
        public void uncaughtException(Thread t, Throwable e) {
            Logger logger = Logger.getAnonymousLogger();
            logger.log(Level.SEVERE, "Thread terminated with exception: " + t.getName(), e);
        }
    }
}
