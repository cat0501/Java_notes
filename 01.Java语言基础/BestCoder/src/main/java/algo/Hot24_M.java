package algo;

/**
 * 两两交换链表中的节点
 *
 * 给你一个链表，两两交换其中相邻的节点，并返回交换后链表的头节点。
 * 你必须在不修改节点内部的值的情况下完成本题（即，只能进行节点交换）。
 */
public class Hot24_M {
    public static void main(String[] args) {

        ListNode head = new ListNode();
        ListNode next1 = new ListNode();
        ListNode next2 = new ListNode();
        ListNode next3 = new ListNode();

        head.val = 1;
        next1.val = 2;
        next2.val = 3;
        next3.val = 4;

        head.next = next1;
        next1.next = next2;
        next2.next = next3;

        ListNode swapPairs = new Solution24().swapPairs(head);
        ListNode p = swapPairs;
        while (p != null){
            System.out.println(swapPairs.val);
            p = p.next;
        }
    }
}



class Solution24 {
    public ListNode swapPairs(ListNode head) {
        if (head == null ||head.next == null){
            return head;
        }

        ListNode pre = new ListNode(0);
        pre.next = head;

        ListNode tmp = pre;
        while (tmp.next != null && tmp.next.next != null){

        }
        return head;
    }
}

/**
 * Definition for singly-linked list.
 */
 // public class ListNode {
 //      int val;
 //      ListNode next;
 //      ListNode() {}
 //      ListNode(int val) { this.val = val; }
 //      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 // }