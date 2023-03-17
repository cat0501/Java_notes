package basic.thread.synchronized2022;

/**
 * @author cat
 * @description
 * @date 2022/6/1 下午8:05
 */

// 定义了两个使用synchronized修饰的普通方法，然后在main函数中定义对象的实例 并发执行各个方法。
// 我们看到，线程 1会等待线程 2执行完成才能执行，这是因为synchronized锁住了当前的对象实例synchronizedDemo1导致的。
public class SynchronizedDemo1 {


    public static void main(String[] args) {
        final SynchronizedDemo1 synchronizedDemo1 = new SynchronizedDemo1();
        final SynchronizedDemo1 synchronizedDemo2 = new SynchronizedDemo1();

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
                //synchronizedDemo1.generalMethod2();
                synchronizedDemo2.generalMethod2();
            }
        }).start();

    }

    // 修饰普通的同步方法，锁住的是当前实例对象
    public synchronized void generalMethod1(){
        try {
            for (int i = 0; i < 3; i++) {
                System.out.println("generalMethod1 execute " + i + "time");
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 修饰普通的同步方法，锁住的是当前实例对象
    public synchronized void generalMethod2(){
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
