package datax;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonTest001 {
    public static void main(String[] args) {
        // 指定 JSON 文件路径
        // /opt/assets/../security/oraclewriter01.json
        String filePath = "\\datax\\oraclewriter01.json";

        try {
            // 读取文件内容到字符串
            String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));
            System.out.println(jsonString);

            // 将字符串解析为 JSONObject
            JSONObject jsonObject = JSON.parseObject(jsonString);
            System.out.println(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
