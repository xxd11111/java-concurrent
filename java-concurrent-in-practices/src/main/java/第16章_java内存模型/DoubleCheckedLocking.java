package 第16章_java内存模型;

import net.jcip.annotations.*;

/**
 * 线程可能看见一个正在构造的resource，或者一个错误的，失效的引用，并拿去使用
 */
@NotThreadSafe
public class DoubleCheckedLocking {
    private static Resource resource;

    public static Resource getInstance() {
        if (resource == null) {
            synchronized (DoubleCheckedLocking.class) {
                if (resource == null)
                    resource = new Resource();
            }
        }
        return resource;
    }

    static class Resource {

    }
}
