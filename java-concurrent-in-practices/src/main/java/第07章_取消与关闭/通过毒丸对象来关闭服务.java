package 第07章_取消与关闭;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Description 通过毒丸对象来关闭服务FIFO：当得到这个毒丸对象时，立即停止，一般是在所有工作完成后提交“毒丸”
 * @Author xxd
 * @Date 2021/10/19
 * @Version 1.0
 */
public class 通过毒丸对象来关闭服务 {
    public class IndexingService {
        private static final int CAPACITY = 1000;
        private final File POISON = new File("");
        private final IndexerThread consumer = new IndexerThread();
        private final CrawlerThread producer = new CrawlerThread();
        private final BlockingQueue<File> queue;
        private final FileFilter fileFilter;
        private final File root;

        public IndexingService(File root, final FileFilter fileFilter) {
            this.root = root;
            this.queue = new LinkedBlockingQueue<File>(CAPACITY);
            this.fileFilter = new FileFilter() {
                public boolean accept(File f) {
                    return f.isDirectory() || fileFilter.accept(f);
                }
            };
        }

        public void start() {
            producer.start();
            consumer.start();
        }

        public void stop() {
            producer.interrupt();
        }

        public void awaitTermination() throws InterruptedException {
            consumer.join();
        }

        private boolean alreadyIndexed(File f) {
            return false;
        }

        //生产者线程
        class CrawlerThread extends Thread {
            public void run() {
                try {
                    crawl(root);
                } catch (InterruptedException e) { /* fall through */
                } finally {
                    while (true) {
                        try {
                            queue.put(POISON);
                            break;
                        } catch (InterruptedException e1) { /* retry */
                        }
                    }
                }
            }

            private void crawl(File root) throws InterruptedException {
                File[] entries = root.listFiles(fileFilter);
                if (entries != null) {
                    for (File entry : entries) {
                        if (entry.isDirectory())
                            crawl(entry);
                        else if (!alreadyIndexed(entry))
                            queue.put(entry);
                    }
                }
            }
        }

        //消费者线程
        class IndexerThread extends Thread {
            public void run() {
                try {
                    while (true) {
                        File file = queue.take();
                        if (file == POISON)
                            break;
                        else
                            indexFile(file);
                    }
                } catch (InterruptedException consumed) {
                }
            }

            public void indexFile(File file) {
                /*...*/
            };
        }


    }
}
