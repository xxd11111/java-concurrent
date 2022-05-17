# 并发容器

## 1.concurrentHashMap
    分段锁实现，使用与hashmap类似
    结构，初始化，定位，操作  看源码

## 2.ConcurrentLinkedQueue
    ConcurrentLinkedQueue是一个基于链接节点的无界线程安全队列，它采用先进先出的规则对节点进行排序，当我们添加一个元素的时候，它会添加到队列的尾部；
    当我们获取一个元素时，它会返回队列头部的元素。它采用了“wait-free”算法（即CAS算法）来实现，该算法在 Michael&Scott算法上进行了一些修改。

## 3.阻塞队列
    
### ArrayBlockingQueue：一个由数组结构组成的有界阻塞队列。
    ArrayBlockingQueue是一个用数组实现的有界阻塞队列。此队列按照先进先出（FIFO）的原 则对元素进行排序。 
    默认情况下不保证线程公平的访问队列，所谓公平访问队列是指阻塞的线程，可以按照 阻塞的先后顺序访问队列，
    即先阻塞线程先访问队列。非公平性是对先等待的线程是非公平 的，当队列可用时，阻塞的线程都可以争夺访问队列的资格，
    有可能先阻塞的线程最后才访问 队列。为了保证公平性，通常会降低吞吐量。我们可以使用以下代码创建一个公平的阻塞队 列。
    ArrayBlockingQueue fairQueue = new ArrayBlockingQueue(1000,true);

    访问者的公平性是使用可重入锁实现的，代码如下。
    public ArrayBlockingQueue(int capacity, boolean fair) {
        if (capacity <= 0) throw new IllegalArgumentException();
        this.items = new Object[capacity];
        lock = new ReentrantLock(fair);
        notEmpty = lock.newCondition();
        notFull = lock.newCondition();
    }

### LinkedBlockingQueue：一个由链表结构组成的有界阻塞队列。
    LinkedBlockingQueue是一个用链表实现的有界阻塞队列。
    此队列的默认和最大长度为 Integer.MAX_VALUE。
    此队列按照先进先出的原则对元素进行排序。
### PriorityBlockingQueue：一个支持优先级排序的无界阻塞队列。
    PriorityBlockingQueue是一个支持优先级的无界阻塞队列。默认情况下元素采取自然顺序 升序排列。也可以自定义类实现compareTo()方法来指定元素排序规则，或者初始化 PriorityBlockingQueue时，指定构造参数Comparator来对元素进行排序。需要注意的是不能保证 同优先级元素的顺序。
### DelayQueue：一个使用优先级队列实现的无界阻塞队列。
    DelayQueue是一个支持延时获取元素的无界阻塞队列。队列使用PriorityQueue来实现。队 列中的元素必须实现Delayed接口，在创建元素时可以指定多久才能从队列中获取当前元素。 只有在延迟期满时才能从队列中提取元素。

    DelayQueue非常有用，可以将DelayQueue运用在以下应用场景。
    缓存系统的设计：可以用DelayQueue保存缓存元素的有效期，使用一个线程循环查询 DelayQueue，一旦能从DelayQueue中获取元素时，表示缓存有效期到了。
    定时任务调度：使用DelayQueue保存当天将会执行的任务和执行时间，一旦从 DelayQueue中获取到任务就开始执行，比如TimerQueue就是使用DelayQueue实现的。
### SynchronousQueue：一个不存储元素的阻塞队列。
    SynchronousQueue是一个不存储元素的阻塞队列。每一个put操作必须等待一个take操作， 否则不能继续添加元素。 
    它支持公平访问队列。默认情况下线程采用非公平性策略访问队列。使用以下构造方法 可以创建公平性访问的SynchronousQueue，如果设置为true，则等待的线程会采用先进先出的 顺序访问队列。
    SynchronousQueue可以看成是一个传球手，负责把生产者线程处理的数据直接传递给消费 者线程。
    队列本身并不存储任何元素，非常适合传递性场景。SynchronousQueue的吞吐量高于 LinkedBlockingQueue和ArrayBlockingQueue。
### LinkedTransferQueue：一个由链表结构组成的无界阻塞队列。
    LinkedTransferQueue是一个由链表结构组成的无界阻塞TransferQueue队列。相对于其他阻 塞队列，LinkedTransferQueue多了tryTransfer和transfer方法。
    （1）transfer方法 如果当前有消费者正在等待接收元素（消费者使用take()方法或带时间限制的poll()方法 时），transfer方法可以把生产者传入的元素立刻transfer（传输）给消费者。如果没有消费者在等 待接收元素，transfer方法会将元素存放在队列的tail节点，并等到该元素被消费者消费了才返 回。transfer方法的关键代码如下。
        Node pred = tryAppend(s, haveData); 
        return awaitMatch(s, pred, e, (how == TIMED), nanos);
    第一行代码是试图把存放当前元素的s节点作为tail节点。第二行代码是让CPU自旋等待 消费者消费元素。因为自旋会消耗CPU，所以自旋一定的次数后使用Thread.yield()方法来暂停 当前正在执行的线程，并执行其他线程。

    （2）tryTransfer方法 tryTransfer方法是用来试探生产者传入的元素是否能直接传给消费者。如果没有消费者等 待接收元素，则返回false。和transfer方法的区别是tryTransfer方法无论消费者是否接收，方法 立即返回，而transfer方法是必须等到消费者消费了才返回。 对于带有时间限制的tryTransfer（E e，long timeout，TimeUnit unit）方法，试图把生产者传入 的元素直接传给消费者，但是如果没有消费者消费该元素则等待指定的时间再返回，如果超 时还没消费元素，则返回false，如果在超时时间内消费了元素，则返回true。
### LinkedBlockingDeque：一个由链表结构组成的双向阻塞队列。
    LinkedBlockingDeque是一个由链表结构组成的双向阻塞队列。所谓双向队列指的是可以 从队列的两端插入和移出元素。双向队列因为多了一个操作队列的入口，在多线程同时入队 时，也就减少了一半的竞争。相比其他的阻塞队列，LinkedBlockingDeque多了addFirst、 addLast、offerFirst、offerLast、peekFirst和peekLast等方法，以First单词结尾的方法，表示插入、 获取（peek）或移除双端队列的第一个元素。以Last单词结尾的方法，表示插入、获取或移除双 端队列的最后一个元素。另外，插入方法add等同于addLast，移除方法remove等效于 removeFirst。但是take方法却等同于takeFirst，不知道是不是JDK的bug，使用时还是用带有First 和Last后缀的方法更清楚。
    在初始化LinkedBlockingDeque时可以设置容量防止其过度膨胀。另外，双向阻塞队列可以 运用在“工作窃取”模式中。

## 4.fork/join框架
    工作窃取（work-stealing）算法是指某个线程从其他队列里窃取任务来执行。那么，为什么 需要使用工作窃取算法呢？
    假如我们需要做一个比较大的任务，可以把这个任务分割为若干 互不依赖的子任务，为了减少线程间的竞争，把这些子任务分别放到不同的队列里，
    并为每个 队列创建一个单独的线程来执行队列里的任务，线程和队列一一对应。比如A线程负责处理A 队列里的任务。
    但是，有的线程会先把自己队列里的任务干完，而其他线程对应的队列里还有 任务等待处理。干完活的线程与其等着，不如去帮其他线程干活，
    于是它就去其他线程的队列 里窃取一个任务来执行。而在这时它们会访问同一个队列，所以为了减少窃取任务线程和被 窃取任务线程之间的竞争，
    通常会使用双端队列，被窃取任务线程永远从双端队列的头部拿 任务执行，而窃取任务的线程永远从双端队列的尾部拿任务执行。