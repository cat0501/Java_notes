/**
 * @author cat
 * @description
 * @date 2022/6/15 下午8:26
 */
public class M2 {
    public static void main(String[] args) {
        //Integer i = new Integer(100);
        //Integer j = new Integer(100);
        //System.out.print(i == j);

        //Integer i = new Integer(100);
        //Integer j = 100;
        //System.out.print(i == j);

        //Integer i = 100;
        //Integer j = 100;
        //System.out.print(i == j);

        //Integer i = 128;
        //Integer j = 128;
        //System.out.print(i == j);
    }
}


/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution2 {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // 新链表头指针，用来指向头结点，返回结果。
        ListNode pre = new ListNode(0);
        // 可移动的一个指针
        ListNode cur = pre;
        // 进位数
        int carry = 0;

        while (l1 != null || l2 != null) {
            int x = l1 != null ? l1.val : 0;
            int y = l2 != null ? l2.val : 0;

            int sum = x + y + carry;

            // 计算进位数
            carry = sum / 10;
            // 实际存入链表的数
            sum = sum % 10;
            cur.next = new ListNode(sum);

            cur = cur.next;

            if (l1 != null){
                l1 = l1.next;
            }
            if (l2 != null){
                l2 = l2.next;
            }
        }

        if (carry == 1){
            cur.next = new ListNode(1);
        }

        return pre.next;
    }
}

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}



