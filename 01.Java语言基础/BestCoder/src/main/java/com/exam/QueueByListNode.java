package com.exam;

import java.util.NoSuchElementException;

/**
 * @author cat
 * @description 单链表实现队列
 * @date 2023/10/2 18:39
 */
public class QueueByListNode {
    public static void main(String[] args) {
        //LinkedList linkedList = new LinkedList<>();

        MyQueue queue = new MyQueue();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        // 打印所有元素
        queue.printAllElement();

        System.out.println(queue.dequeue());  // 输出：1

        System.out.println(queue.peek());     // 输出：2
        System.out.println(queue.dequeue());  // 输出：2

        queue.enqueue(5);

        System.out.println(queue.dequeue()); // 输出：3
        System.out.println(queue.dequeue()); // 输出：5
        System.out.println(queue.isEmpty());  // 输出：true
    }
}

// 自定义单链表结点
class MyNode {
    int data;
    MyNode next;

    public MyNode(int data) {
        this.data = data;
        this.next = null;
    }
}

// 自定义队列（包如入队列、出队列、获取队列顶部元素等操作）
class MyQueue {
    MyNode head;
    MyNode tail;

    public MyQueue() {
        this.head = null;
        this.tail = null;
    }

    // 打印所有元素
    public void printAllElement(){
        System.out.println("队列元素：");
        MyNode currentNode = head;
        while (currentNode != null){
            int data = currentNode.data;
            System.out.println(data);
            currentNode = currentNode.next;
        }
        System.out.println("打印结束");
    }

    // 判空
    public boolean isEmpty() {
        return head == null;
    }

    // 入队
    public void enqueue(int data) {
        MyNode newNode = new MyNode(data);

        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    // 出队列
    public int dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("队列为空");
        }

        int data = head.data;
        head = head.next;

        if (isEmpty()) {
            tail = null;
        }

        return data;
    }

    // 获取队列顶部元素
    public int peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("队列为空");
        }

        return head.data;
    }
}


