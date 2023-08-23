package com.bs.datax;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class JsonTest001 {
    public static void main(String[] args) {
        // 指定 JSON 文件路径
        // /opt/assets/../security/oraclewriter01.json
        String filePath = "datax/mysql_to_hive.json";
        System.out.println(Paths.get(filePath));

        StringBuilder sb = new StringBuilder();

        InputStream resourceAsStream = JsonTest001.class.getResourceAsStream("test.json");

        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(resourceAsStream,"UTF-8"));
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                if (StringUtils.isNotBlank(line)) {
                    sb.append(line);
                }
            }
            System.out.println(sb.toString());
        } catch (Exception e) {
            log.error("", e);
        }

        try {
            Path path = Paths.get(filePath).toAbsolutePath();
            System.out.println("文件绝对路径：" + path);
            // 读取文件内容到字符串
            String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));
            System.out.println(jsonString);

            // 将字符串解析为 JSONObject
            JSONObject jsonObject = JSON.parseObject(jsonString);
            System.out.println(jsonObject);
        } catch (Exception e) {
            log.error("", e);
        }
    }
}
