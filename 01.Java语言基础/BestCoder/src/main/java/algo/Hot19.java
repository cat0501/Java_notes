package algo;

/**
 * @author cat
 * @description
 * @date 2023/9/28 19:17
 */
public class Hot19 {
    public static void main(String[] args) {
        // 用例1
        //ListNode head = new ListNode();
        //ListNode next1 = new ListNode();
        //ListNode next2 = new ListNode();
        //ListNode next3 = new ListNode();
        //ListNode next4 = new ListNode();
        //
        //head.val = 1;
        //next1.val = 2;
        //next2.val = 3;
        //next3.val = 4;
        //next4.val = 5;
        //
        //head.next = next1;
        //next1.next = next2;
        //next2.next = next3;
        //next3.next = next4;
        //
        //int n = 2;


        // 用例2
        //ListNode head = new ListNode();
        //ListNode next1 = new ListNode();
        //head.val = 1;
        //next1.val = 2;
        //head.next = next1;
        //int n = 1;

        // 用例2
        ListNode head = new ListNode();
        ListNode next1 = new ListNode();
        ListNode next2 = new ListNode();
        head.val = 1;
        next1.val = 2;
        next2.val = 3;
        head.next = next1;
        next1.next = next2;
        int n = 1;

        // 遍历链表
        //ListNode curNode = head;
        //while (curNode.next != null){
        //    System.out.println(curNode.val);
        //    curNode = curNode.next;
        //}

        ListNode listNode = new Solution().removeNthFromEnd(head, n);
        ListNode curNode = listNode;
        while (curNode != null){
            System.out.println(curNode.val);
            curNode = curNode.next;
        }
    }
}


class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head.next == null) {
            return null;
        }

        // 获取结点数量
        int total = 1;
        ListNode item = head;
        while (item.next != null){
            total += 1;
            item = item.next;
        }
        System.out.println("total: " + total);

        // 获取移除元素的位置
        int removePos;
        removePos = (n == total) ? total : total - n + 1;
        System.out.println("removePos: " + removePos);

        // 获取删除结点前一个结点的引用
        ListNode removeItem = head;
        for (int i = 1; i < removePos - 1; i++) {
            removeItem = removeItem.next;
        }
        if (n == total){
            removeItem = removeItem.next;
        }
        System.out.println("removeItem val: " + removeItem.val);

        // 修改引用删除节点
        if (n == total){
            System.out.println("删除的是链表头结点");
            head = head.next;
        } else if (n == 1){
            System.out.println("删除的是链表尾结点");
            removeItem.next = null;
        } else {
            System.out.println("删除的是链表中间结点");
            removeItem.next = removeItem.next.next;
        }
        return head;
    }
}

/**
 * Definition for singly-linked list.
 */
class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}
