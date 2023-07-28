package basic.java8;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Lemonade
 * @updateTime 2023/7/24 11:37
 */
public class ExceptionTest01 {
    public static void main(String[] args) {

        // 测试 Long[] 转 String
        Long[] along = {1L, 200L};
        System.out.println(Arrays.toString(along));

        // 测试打印[]
        System.out.println(new ArrayList<>());

        // 测试throw异常结果
        int i = 0;
        if (i == 0){
            throw new RuntimeException("11");
        }
        System.out.println("123");
    }
}
