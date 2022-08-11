/**
 * @author cat
 * @description 单例实现：懒汉模式（线程安全）
 * @date 2022/6/21 上午10:42
 */
public class LazySingleton {
    // 私有的静态（属于类）对象，能够很好地保证单例对象的唯一性。
    private static LazySingleton instance;

    private LazySingleton() {
    }

    // 加锁的静态方法获取该对象，线程安全。
    public static synchronized LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
}
