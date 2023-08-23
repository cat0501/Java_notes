package com.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @author cat
 * @description
 * @date 2023/8/19 08:48
 */
@Slf4j
@Controller
public class MultipartFileController {

    @PostMapping("/read/test")
    public String testReadMultipartFile(MultipartFile multipartFile, boolean isFirstTitle, int startLine) {

        // 获取文件名
        String fileName = multipartFile.getOriginalFilename();

        // 从MultipartFile获取输入流
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        // 创建一个新的MultipartFile来保存修改后的内容
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
        String line = "";

        int count = 0;
        // 是否首行为标题
        if (isFirstTitle) {
            try {
                if (reader != null) {
                    line = reader.readLine();
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
            count = 1;
        }

        // 跳过行数
        for (int i = 0; i < startLine - count; i++) {
            try {
                if (reader != null) {
                    reader.readLine();
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }

        return "ok";
    }

}
