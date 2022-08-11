/**
 * @author cat
 * @description
 * @date 2022/6/21 上午10:48
 */
public class HungrySingleton {
    private static HungrySingleton instance = new HungrySingleton();

    private HungrySingleton() {
    }

    public static HungrySingleton getInstance(){
        return instance;
    }
}
