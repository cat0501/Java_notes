package design_pattern.singleton;

/**
 * @author cat
 * @description
 * @date 2022/6/2 上午10:55
 */
public class LazySingleton {

    // 定义一个私有的静态对象
    private static LazySingleton instance;

    public LazySingleton() {
    }

    // 懒汉模式在获取对象实例时做了加锁操作，因此是线程安全的
    public static synchronized LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
}
