package basic.design_pattern.singleton;

/**
 * @author cat
 * @description
 * @date 2022/6/2 上午10:59
 */
public class HungrySingleton {

    // 饿汉模式是在定义单例对象的同时将其实例化的，直接使用便可。
    // 也就是说，在饿汉模式下，在Class Loader完成后该类的实例便已经存在于JVM中了
    private static HungrySingleton instance = new HungrySingleton();

    public HungrySingleton() {
    }
    public static HungrySingleton getInstance() {
        return instance;
    }
}
