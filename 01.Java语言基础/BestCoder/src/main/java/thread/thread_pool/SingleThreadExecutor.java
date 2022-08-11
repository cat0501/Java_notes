package thread.thread_pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author cat
 * @description
 * @date 2022/6/1 下午3:26
 */
public class SingleThreadExecutor {

    public static void main(String[] args) {

        ExecutorService singleThread = Executors.newSingleThreadExecutor();

    }
}
