package 第07章_Java中13个原子操作;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author xxd
 * @description AtomicReference的使用示例代码
 *
 * 代码中首先构建一个user对象，然后把user对象设置进AtomicReference中，
 * 最后调用 compareAndSet方法进行原子更新操作，
 * 实现原理同AtomicInteger里的compareAndSet方法。
 * @date 2022-05-17
 */
public class L03_AtomicReferenceTest {
    public static AtomicReference<User> atomicUserRef = new AtomicReference<User>();

    public static void main(String[] args) {
        User user = new User("conan", 15);
        atomicUserRef.set(user);
        User updateUser = new User("Shinichi", 17);
        atomicUserRef.compareAndSet(user, updateUser);
        System.out.println(atomicUserRef.get().getName());
        System.out.println(atomicUserRef.get().getOld());
    }

    static class User {
        private String name;
        private int old;

        public User(String name, int old) {
            this.name = name;
            this.old = old;
        }

        public String getName() {
            return name;
        }

        public int getOld() {
            return old;
        }
    }
}
