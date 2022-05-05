package 第08章_线程池的使用;

import javax.xml.bind.Element;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @Description 将串行执行转化为并行执行
 * @Author xxd
 * @Date 2021/10/21
 * @Version 1.0
 */
public class 将串行执行转化为并行执行 {
    void processSequentially(List<Element> elements){
        for (Element element : elements) {
            process(element);
        }
    }

    private void process(Element element) {
    }

    void processInParallel(Executor executor, List<Element> elements){
        for (final Element element : elements) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    process(element);
                }
            });
        }
    }
}
