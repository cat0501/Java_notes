package basic.java8;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Lemonade
 * @description
 * @updateTime 2023/7/24 11:37
 */
public class ExceptionTest01 {
    public static void main(String[] args) {
        System.out.println("0,100,131,134,137,138,139,140,141,142,143,144,145".length());

        Long[] along = {1L, 200L};
        System.out.println(Arrays.toString(along));

        System.out.println(new ArrayList<>());
        int i = 0;
        if (i == 0){
            throw new RuntimeException("11");
        }
        System.out.println("123");
    }
}
