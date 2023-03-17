package basic.collection_map;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author cat
 * @description
 * @date 2022/6/2 下午7:28
 */
public class ListTest {
    public static void main(String[] args) {

        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("1");

        }
        list.add("1");
        list.get(0);
        System.out.println(list.size());


        LinkedList<Object> linkedList = new LinkedList<>();

    }
}
