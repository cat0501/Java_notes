package basic.design_pattern.singleton;

/**
 * @author cat
 * @description
 * @date 2022/6/2 上午11:06
 */
public class Lock2Singleton {

    private volatile static Lock2Singleton singleton;//对象锁

    public Lock2Singleton() {
    }
    public static Lock2Singleton getInstance() {
        if (singleton == null) {
            synchronized (Lock2Singleton.class){//方法锁
                if (singleton == null){
                    singleton = new Lock2Singleton();
                }
            }
        }
        return singleton;
    }
}
