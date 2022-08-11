package design_pattern.singleton;

/**
 * @author cat
 * @description
 * @date 2022/6/2 上午11:02
 */

// 之所以这样设计，是因为类的静态内部类在JVM中是唯一的，这很好地保障了单例对象的唯一性
public class Singleton {

    // 在类中定义一个静态内部类，将对象实例的定义和初始化放在内部类中完成
    private static class SingletonHolder {
        private static final Singleton INSTANCE = new Singleton();
    }

    public Singleton() {
    }

    public static final Singleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
