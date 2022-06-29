package lru;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @author cat
 * @description
 * @date 2022/6/8 下午10:31
 */


public class M146 {

    public static void main(String[] args) {

        //["LRUCache","put","put","put","put","get","get"]
        //[[2],[2,1],[1,1],[2,3],[4,1],[1],[2]]
        //LRUCache lruCache = new LRUCache(2);
        //lruCache.put(2, 1);
        //lruCache.put(1, 1);
        //lruCache.put(2, 3);
        //lruCache.put(4, 1);
        //lruCache.get(1);
        //lruCache.get(2);
        //
        //HashMap<Integer, Node> map = lruCache.map;
        //System.out.println(map.keySet());

        LRUCache2 lruCache2 = new LRUCache2(1);
        lruCache2.put(2, 1);
        System.out.println(lruCache2.cache);
        System.out.println(lruCache2.cache.get(2));
        System.out.println(lruCache2.get(2));
    }


}


// LRU 缓存算法的核⼼数据结构就是哈希链表，双向链表和哈希表的结合体。
class Node{
    public int key, val;

    public Node(int key, int val) {
        this.key = key;
        this.val = val;
    }
    public Node next;
    public Node prev;
}

class DoubleList{
    // 头尾虚结点
    private Node head, tail;
    // 链表元素数
    private int size;

    public DoubleList() {
        head = new Node(0, 0);
        tail = new Node(0, 0);
        size = 0;
        head.next = tail;
        tail.prev = head;
    }

    // 在链表尾部添加节点 x，时间 O(1)
    public void addLast(Node node) {
        node.prev = tail.prev;
        node.next = tail;

        tail.prev.next = node;
        tail.prev = node;
        size++;
    }

    // 删除链表中的 x 节点（x ⼀定存在）
    // 由于是双链表且给的是⽬标 Node 节点，时间 O(1)
    public void remove(Node x) {
        x.prev.next = x.next;
        x.next.prev = x.prev;
        size--;
    }

    // 删除链表中第⼀个节点，并返回该节点，时间 O(1)
    public Node removeFirst() {
        if (head.next == null) return null;

        Node first = head.next;
        remove(first);
        return first;
    }

    // 返回链表长度，时间 O(1)
    public int size(){
        return size;
    }

}


class LRUCache {
    // key -> Node(key, val)
    public HashMap<Integer, Node> map;
    // Node(k1, v1) <-> Node(k2, v2)...
    public DoubleList cache;
    // 最⼤容量
    private int cap;

    // ⾸先要接收⼀个 capacity 参数作为缓存的最⼤容量，然后实现两个 API
    public LRUCache(int capacity) {
        this.cap = capacity;
        map = new HashMap<>();
        cache = new DoubleList();
    }

    /* 将某个 key 提升为最近使⽤的 */
    private void makeRecently(int key) {
        Node x = map.get(key);
        // 先从链表中删除这个结点
        cache.remove(x);
        // 重新插到队尾
        cache.addLast(x);
    }
    /* 添加最近使⽤的元素 */
    private void addRecently(int key, int val) {
        Node x = new Node(key, val);
        cache.addLast(x);
        // 别忘了在 map 中添加 key 的映射
        map.put(key, x);
    }

    /* 删除某⼀个 key */
    private void deleteKey(int key) {
        Node x = map.get(key);
        // 从链表中删除
        cache.remove(x);
        // 从 map 中删除
        map.remove(key);
    }

    /* 删除最久未使⽤的元素 */
    private void removeLeastRecently() {
        // 链表头部的第⼀个元素就是最久未使⽤的
        Node x = cache.removeFirst();
        // 同时别忘了从 map 中删除它的 key
        map.remove(x.key);
    }



    // 另⼀个是 get(key) ⽅法获取 key 对应的 val，如果 key 不存在则返回 -1。
    public int get(int key) {
        if (!map.containsKey(key)){
            return -1;
        }
        Node x = map.get(key);
        makeRecently(x.key);
        return map.get(key).val;
    }

    // ⼀个是 put(key, val) ⽅法存 ⼊键值对
    // 较复杂
    public void put(int key, int value) {
        // 若key 已存在
        if (map.containsKey(key)){
            //// 删除旧的数据
            //deleteKey(key);
            //// 新插⼊的数据为最近使⽤的数据
            //addRecently(key, value);
            //return;

            // 修改值
            map.get(key).val = value;
            // 将key 提升为最近使用
            makeRecently(key);
            return;
        }

        if (cap == cache.size()){
            // 淘汰最久未使用的key
            removeLeastRecently();
        }
        addRecently(key, value);
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */

class LRUCache2 {
    int cap;
    LinkedHashMap<Integer, Integer> cache = new LinkedHashMap<>();

    private void makeRecently(int key) {
        Integer value = cache.get(key);
        //System.out.println("makeRecently.key=" + value);
        // 删除key，重新插入到队尾
        cache.remove(key);
        cache.put(key, value);
    }

    public LRUCache2(int capacity) {
        this.cap = capacity;
    }

    public int get(int key) {
        if (!cache.containsKey(key)){
            return -1;
        }
        makeRecently(key);
        return cache.get(key);
    }

    public void put(int key, int value) {
        if (cache.containsKey(key)){
            // 修改key值
            cache.put(key, value);
            // 将key变为最近使用
            makeRecently(key);
            return;
        }

        if (cache.size() > this.cap){
            Integer oldestKey = cache.keySet().iterator().next();
            cache.remove(oldestKey);
        }
        cache.put(key, value);
    }
}





