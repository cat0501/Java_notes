package basic.json;

/**
 * @author Lemonade
 * @description
 * @updateTime 2023/5/18 15:12
 */
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Example {
    public static void main(String[] args) {
        String jsonString = "{\"name\":\"Alice\",\"age\":20,\"hobbies\":[\"reading\",\"swimming\",\"traveling\"]}";
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        JSONArray hobbiesArray = jsonObject.getJSONArray("hobbies");
        for (int i = 0; i < hobbiesArray.size(); i++) {
            String hobby = hobbiesArray.getString(i);
            System.out.println("Hobby " + (i + 1) + ": " + hobby);
        }
    }
}
