package basic.thread.thread_pool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @author cat
 * @description
 * @date 2022/6/7 上午9:54
 */
public class FutureTest1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 创建FutureTask
        FutureTask<Integer> futureTask = new FutureTask<>(() -> 1 + 2);
        // 创建线程池
        ExecutorService es = Executors.newCachedThreadPool();
        // 提交FutureTask
        es.submit(futureTask);
        // 获取计算结果
        Integer result = futureTask.get();
        System.out.println(result);
    }
}
