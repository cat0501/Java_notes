package com.bs.io;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @author cat
 * @description
 * @date 2023/8/19 08:36
 */

@Slf4j
public class MultipartFileTest {


    public static void main(String[] args) {

    }


    // 文件处理
    private MultipartFile processUploadFile(MultipartFile file, String separator, int startLine, boolean isFirstTitle, int tableId) {

        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 从MultipartFile获取输入流
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
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

        while (true) {
            try {
                if ((line = reader.readLine()) == null) break;
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
            // 将修改后的行写入输出流
            try {
                writer.write(line);
                writer.newLine();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        try {
            writer.flush();
            writer.close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        // 将修改后的内容转换为字节数组并创建新的MultipartFile对象
        byte[] bytes = outputStream.toByteArray();
        return new MultipartFile() {
            @Override
            public String getName() {
                return file.getName();
            }

            @Override
            public String getOriginalFilename() {
                return fileName;
            }

            @Override
            public String getContentType() {
                return file.getContentType();
            }

            @Override
            public boolean isEmpty() {
                return bytes.length == 0;
            }

            @Override
            public long getSize() {
                return bytes.length;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return bytes;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream(bytes);
            }

            @Override
            public void transferTo(File file) throws IOException, IllegalStateException {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(bytes);
                fileOutputStream.close();
            }
        };
    }
}
