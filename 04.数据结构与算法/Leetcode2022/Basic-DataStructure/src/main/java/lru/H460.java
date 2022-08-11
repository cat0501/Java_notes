package lru;

import java.util.HashMap;
import java.util.LinkedHashSet;

/**
 * @author cat
 * @description
 * @date 2022/6/9 下午9:06
 */
public class H460 {

    public static void main(String[] args) {











    }
}

// 最近最少使用算法 🍋

class LFUCache {

    // key 到 val的映射，KV表
    HashMap<Integer, Integer> keyToVal;
    // key 到 freq的映射，KF表
    HashMap<Integer, Integer> keyToFreq;
    // freq 到 key的映射，FK表
    HashMap<Integer, LinkedHashSet<Integer>> freqToKeys;

    // 记录最小的频次
    int minFreq;
    // 记录LFU缓存的最大容量
    int cap;


    public LFUCache(int capacity) {
        keyToVal = new HashMap<>();
        keyToFreq = new HashMap<>();
        freqToKeys = new HashMap<>();
        this.cap = capacity;
        this.minFreq = 0;
    }

    public int get(int key) {
        return 0;
    }

    public void put(int key, int value) {

    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
