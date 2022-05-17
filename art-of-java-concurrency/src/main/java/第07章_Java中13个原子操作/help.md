# 并发容器

## 1.AtomicInteger getAndIncrement的源码

````
AtomicBoolean：原子更新布尔类型。 
AtomicInteger：原子更新整型。 
AtomicLong：原子更新长整型。

    public final int getAndIncrement() {
        for (; ; ) {
            int current = get();
            int next = current + 1;
            if (compareAndSet(current, next)) return current;
        }
    }

    public final boolean compareAndSet(int expect, int update) {
        return unsafe.compareAndSwapInt(this, valueOffset, expect, update);
    }
````

## 2.Unsafe的源码

````
    /**
     * 如果当前数值是expected，则原子的将Java变量更新成x 
     * @return 如果更新成功则返回true
     */
    public final native boolean compareAndSwapObject(Object o, long offset, Object expected, Object x);

    public final native boolean compareAndSwapInt(Object o, long offset, int expected, int x);

    public final native boolean compareAndSwapLong(Object o, long offset, long expected, long x);
    
    Unsafe只提供了3种CAS方法：compareAndSwapObject、compare- AndSwapInt和compareAndSwapLong，
    再看AtomicBoolean源码，发现它是先把Boolean转换成整型，再使用compareAndSwapInt进行CAS，
    所以原子更新char、float和double变量也可以用类似的思路来实现。
````

## 3.原子更新数组

````
    AtomicIntegerArray：原子更新整型数组里的元素。
    AtomicLongArray：原子更新长整型数组里的元素。
    AtomicReferenceArray：原子更新引用类型数组里的元素。
    AtomicIntegerArray类主要是提供原子的方式更新数组里的整型。
    
    需要注意的是，数组value通过构造方法传递进去，然后AtomicIntegerArray会将当前数组复制一份，
    所以当AtomicIntegerArray对内部的数组元素进行修改时，不会影响传入的数组。
````

## 4.原子更新引用类型

````
AtomicReference：原子更新引用类型。
AtomicReferenceFieldUpdater：原子更新引用类型里的字段。
AtomicMarkableReference：原子更新带有标记位的引用类型。

````

## 5.原子更新字段类

````
如果需原子地更新某个类里的某个字段时，就需要使用原子更新字段类，Atomic包提供 了以下3个类进行原子字段更新。

AtomicIntegerFieldUpdater：原子更新整型的字段的更新器。
AtomicLongFieldUpdater：原子更新长整型字段的更新器。
AtomicStampedReference：原子更新带有版本号的引用类型。该类将整数值与引用关联起 来，可用于原子的更新数据和数据的版本号，可以解决使用CAS进行原子更新时可能出现的 ABA问题。

 * 要想原子地更新字段类需要两步。
 * 第一步，因为原子更新字段类都是抽象类，每次使用的 时候必须使用静态方法newUpdater()创建一个更新器，并且需要设置想要更新的类和属性。
 * 第二步，更新类的字段（属性）必须使用public volatile修饰符。

````