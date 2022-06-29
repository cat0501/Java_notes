/**
 * @author cat
 * @description
 * @date 2022/6/21 上午10:54
 */
public class Singleton {

    // 静态内部类：对象实例的定义和初始化 放在内部类中进行
    private static class SingletonHolder {
        private static final Singleton  INSTANCE = new Singleton();
    }

    private Singleton(){}

    public static final Singleton getInstance(){
        return SingletonHolder.INSTANCE;
    }
}
