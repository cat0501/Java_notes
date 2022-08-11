package thread.synchronized2022;

/**
 * @author cat
 * @description
 * @date 2022/6/1 下午8:29
 */
public class SynchronizedDemo {

    String lockA = "lockA";

    public static void main(String[] args) {

        final SynchronizedDemo synchronizedDemo = new SynchronizedDemo();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronizedDemo.blockMethod1();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronizedDemo.blockMethod2();
            }
        }).start();

    }


    // 用于方法块,锁住的是在括号里面配置的对象
    public void blockMethod1(){
        try {
            synchronized (lockA) {
                for (int i = 0; i < 3; i++) {
                    System.out.println("Method 1 execute");
                    Thread.sleep(3000);
                }
            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void blockMethod2(){
        try {
            synchronized (lockA) {
                for (int i = 0; i < 3; i++) {
                    System.out.println("Method 2 execute");
                    Thread.sleep(3000);
                }
            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

}



