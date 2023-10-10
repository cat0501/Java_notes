package algo;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author cat
 * @description 常用数据结构的定义
 * @date 2023/9/29 17:48
 */
public class Basic {
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(1);
        stack.push(1);

        while (!stack.isEmpty()){
            System.out.println(stack.pop());
        }

        List<Integer> list = new ArrayList<>();
        list.get(0);
    }
}
