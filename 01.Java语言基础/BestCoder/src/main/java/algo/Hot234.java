package algo;

import java.util.Stack;

/**
 * @author cat
 * @description
 * @date 2023/9/29 18:44
 */
public class Hot234 {
    public static void main(String[] args) {

    }


    public boolean isPalindrome(ListNode head) {
        Stack<Integer> inStack = new Stack();
        Stack<Integer> outStack = new Stack();

        while(head != null){
            inStack.push(head.val);
            head = head.next;
        }

        while(!inStack.isEmpty()){
            int cur = inStack.pop();
            if(outStack.isEmpty() ||cur == outStack.peek()){
                outStack.push(cur);
            }
        }

        return outStack.isEmpty();
    }

}
