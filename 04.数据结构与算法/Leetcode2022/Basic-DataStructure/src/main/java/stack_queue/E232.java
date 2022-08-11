package stack_queue;

/**
 * @author cat
 * @description
 * @date 2022/5/30 下午1:27
 */

import java.util.Stack;

/**
 请你仅使用两个栈实现先入先出队列。队列应当支持一般队列支持的所有操作（push、pop、peek、empty）：

 实现 MyQueue 类：

 - void push(int x) 将元素 x 推到队列的末尾
 - int pop() 从队列的开头移除并返回元素
 - int peek() 返回队列开头的元素
 - boolean empty() 如果队列为空，返回 true ；否则，返回 false
 */
public class E232 {

    public static void main(String[] args) {

    }


    /**
     * 解法1：两个栈实现一个队列
     */
    static class MyQueue {

        private Stack<Integer> s1, s2;

        public MyQueue() {
            // 队尾
            s1 = new Stack<Integer>();
            // 队头
            s2 = new Stack<Integer>();
        }

        // void push(int x) 将元素 x 推到队列的末尾
        public void push(int x) {
            s1.push(x);
        }

        // int pop() 从队列的开头移除并返回元素
        public int pop() {
            if (s2.isEmpty()) {
                while (!s1.isEmpty()) {
                    s2.push(s1.pop());
                }
            }
            return s2.pop();
        }

        // int peek() 返回队列开头的元素
        public int peek() {
            if (s2.isEmpty()){
                while (!s1.isEmpty()) {
                    s2.push(s1.pop());
                }
            }
            return s2.peek();
        }

        // boolean empty() 如果队列为空，返回 true ；否则，返回 false
        public boolean empty() {
            return s1.size() == 0 && s2.size() == 0;
        }
    }

/**
 * Your MyQueue object will be instantiated and called as such:
 * MyQueue obj = new MyQueue();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.empty();
 */
}
