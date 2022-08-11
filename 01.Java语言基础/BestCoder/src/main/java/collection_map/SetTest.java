package collection_map;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author cat
 * @description
 * @date 2022/6/2 下午9:10
 */
public class SetTest {
    public static void main(String[] args) {
        // HashSet
        HashSet<String> hashSet = new HashSet<>();
        hashSet.add("5=6");
        hashSet.add("2=3");
        hashSet.add("3=4");
        hashSet.add(null);
        System.out.println(hashSet);


        LinkedHashSet<Object> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add(null);

    }
}
