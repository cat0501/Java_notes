package com.itheima.shiro.utils;

import lombok.extern.log4j.Log4j2;
import org.apache.shiro.codec.Base64;

import java.io.*;

/**
 * @Description：实现shiro会话的序列化存储
 */
@Log4j2
public class ShiroRedissionSerialize {

    public static Object deserialize(String str) {
        if (EmptyUtil.isNullOrEmpty(str)) {
            return null;
        }
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        Object object=null;
        try {
            bis = new ByteArrayInputStream(Base64.decode(str));
            ois = new ObjectInputStream(bis);
            object = ois.readObject();
        } catch (IOException |ClassNotFoundException e) {
            log.error("RedisSessionDao流读取异常：{}",e);
        } finally {
            try {
                bis.close();
                ois.close();
            } catch (IOException e) {
                log.error("RedisSessionDao流读取异常：{}",e);
            }
        }
        return object;
    }

    public static String serialize(Object obj) {

        if (EmptyUtil.isNullOrEmpty(obj)) {
            return null;
        }
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        String base64String = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            base64String = Base64.encodeToString(bos.toByteArray());
        } catch (IOException e) {
            log.error("RedisSessionDao流读取异常：{}",e);
        } finally {
            try {
                bos.close();
                oos.close();
            } catch (IOException e) {
                log.error("RedisSessionDao流读取异常：{}",e);
            }
        }
        return base64String;
    }
}
