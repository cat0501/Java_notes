package stack_queue;

/**
 * @author cat
 * @description
 * @date 2022/5/30 下午1:59
 */

import java.util.LinkedList;
import java.util.Queue;

/**
 请你仅使用两个队列实现一个后入先出（LIFO）的栈，并支持普通栈的全部四种操作（push、top、pop 和 empty）。

 实现 MyStack 类：
 - void push(int x) 将元素 x 压入栈顶。
 - int pop() 移除并返回栈顶元素。
 - int top() 返回栈顶元素。
 - boolean empty() 如果栈是空的，返回 true ；否则，返回 false 。


 链接：https://leetcode.cn/problems/implement-stack-using-queues

 ⽤队列实现栈是没啥亮点的问题，但是⽤双栈实现队列是值得学习的。
 */
public class E225 {

    /**
     * @description 栈的API
     *
     *
     */

    public static void main(String[] args) {
    }

    static
    class MyStack {

        Queue<Integer> q = new LinkedList<>();
        int top = 0;

        public MyStack() {
        }

        // void push(int x) 将元素 x 压入栈顶。
        public void push(int x) {
            q.offer(x);
            top = x;

        }

        // int pop() 移除并返回栈顶元素。
        public int pop() {
            int size = q.size();
            while (size > 2){
                q.offer(q.poll());
                size --;
            }
            // 记录新的队尾元素
            top = q.peek();
            q.offer(q.poll());
            // 之前的队尾元素已经到了队头
            return q.poll();
        }

        // int top() 返回栈顶元素。
        public int top() {
            return top;
        }

        // boolean empty() 如果栈是空的，返回 true ；否则，返回 false 。
        public boolean empty() {
            return q.isEmpty();
        }
    }

/**
 * Your MyStack object will be instantiated and called as such:
 * MyStack obj = new MyStack();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.top();
 * boolean param_4 = obj.empty();
 */

}
