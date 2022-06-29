/**
 * @author cat
 * @description
 * @date 2022/6/21 下午12:07
 */
public class Lock2Singleton {
    // 对象锁：保障初始化时对象的唯一性
    private volatile static Lock2Singleton singleton;

    public Lock2Singleton() {}

    public static Lock2Singleton getInstance() {
        if (singleton == null) {
            // synchronized方法锁：保障操作的唯一性
            synchronized (Lock2Singleton.class) {
                if (singleton == null){
                    singleton = new Lock2Singleton();
                }
            }
        }

        return singleton;
    }
}
