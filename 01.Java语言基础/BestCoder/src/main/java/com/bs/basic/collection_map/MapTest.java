package com.bs.basic.collection_map;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author cat
 * @description
 * @date 2022/6/2 下午10:38
 */
public class MapTest {
    public static void main(String[] args) {
        HashMap<Object, Object> hashMap = new HashMap<>();
        HashMap<Object, Object> hashMap2 = new HashMap<>(20);
        hashMap.put("name", "hui");
        hashMap.put("age", "22");
        hashMap.put("asex", "2");

        System.out.println(hashMap);

        Set<Map.Entry<Object, Object>> entrySet = hashMap.entrySet();
        for (Map.Entry<Object, Object> objectObjectEntry : entrySet) {
            System.out.println(objectObjectEntry.getKey());
            System.out.println(objectObjectEntry.getValue());

        }
        System.out.println(hashMap.entrySet());


        //System.out.println(1 << 30);
        //int n = 20;
        //n |= n >>> 1;
        //n |= n >>> 2;
        //n |= n >>> 4;
        //n |= n >>> 8;
        //n |= n >>> 16;
        //System.out.println(n);

        //ConcurrentHashMap

    }

}
