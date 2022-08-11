package thread.synchronized2022;

/**
 * @author cat
 * @description
 * @date 2022/6/1 下午8:23
 */
public class SynchronizedDemo2 {

    public static void main(String[] args) {
        final SynchronizedDemo2 synchronizedDemo1 = new SynchronizedDemo2();
        final SynchronizedDemo2 synchronizedDemo2 = new SynchronizedDemo2();

        // 匿名内部类
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronizedDemo1.generalMethod1();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronizedDemo2.generalMethod2();
            }
        }).start();
    }


    // 修饰静态同步方法，锁住的是当前类的 Class对象
    public static synchronized void generalMethod1(){
        try {
            for (int i = 0; i < 3; i++) {
                System.out.println("generalMethod1 execute " + i + "time");
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 修饰静态同步方法，锁住的是当前类的 Class对象
    public static synchronized void generalMethod2(){
        try {
            for (int i = 0; i < 3; i++) {
                System.out.println("generalMethod2 execute " + i + "time");
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
