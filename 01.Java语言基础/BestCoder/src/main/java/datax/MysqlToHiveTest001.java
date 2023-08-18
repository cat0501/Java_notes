package datax;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
public class MysqlToHiveTest001 {

    public static void main(String[] args) throws IOException {
        MysqlToHiveTest001 test = new MysqlToHiveTest001();
        String jsonFileName = "/json/mysql_to_hive.json";

        String jsonString = test.readJsonFile(jsonFileName);
        log.info(jsonString);

        JSONObject jsonObject = JSON.parseObject(jsonString);
        String contentStr = jsonObject.getJSONObject("job").getString("content");
        log.info(contentStr);
    }

    private String readJsonFile(String fileName) throws IOException {
        ClassPathResource resource = new ClassPathResource(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));

        StringBuilder jsonString = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonString.append(line);
        }
        reader.close();

        return jsonString.toString();
    }
}
