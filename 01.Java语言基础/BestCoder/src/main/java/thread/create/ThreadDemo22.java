package thread.create;

/**
 * @author cat
 * @description
 * @date 2022/4/11 下午10:03
 */
public class ThreadDemo22 {
    public static void main(String[] args) {
        // Integer num = 10;
        // int a = num.intValue();

        // 实现Runnable接口(匿名内部类形式)
        Runnable target = new Runnable(){
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println("子线程1执行输出..." + i);
                }
            }
        };

        Thread t = new Thread(target);
        t.start();

        // ------------------------------------------
        new Thread(new Runnable(){
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println("子线程2执行输出..." + i);
                }
            }
        }).start();

        // ------------------------------------------lambda
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("子线程3执行输出..." + i);
            }
        }).start();



        for (int i = 0; i < 10; i++) {
            System.out.println("主线程执行输出..." + i);
        }
    }
}


