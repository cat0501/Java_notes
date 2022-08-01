package com.itheima.shiro.utils;

import lombok.extern.log4j.Log4j2;

import java.io.*;

/**
 * @Description：自定义序列化工具
 */
@Log4j2
public class ShiroRedissionSerialize {

    //序列化方法
    public static String serialize(Object object){
        //判断对象是否为空
        if (EmptyUtil.isNullOrEmpty(object)){
            return null;
        }
        //流的操作
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos =null;
        String encodeBase64 = null;
        bos = new ByteArrayOutputStream();
        try {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(object);
            //转换字符串
            encodeBase64 = EncodesUtil.encodeBase64(bos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            log.error("流写入异常：{}",e);
        }finally {
            //关闭流
            try {
                bos.close();
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
                log.error("流写入异常：{}",e);
            }
        }
        return encodeBase64;
    }

    //反序列化方法
    public static Object deserialize(String str){
        //判断是否为空
        if (EmptyUtil.isNullOrEmpty(str)){
            return null;
        }
        //流从操作
        ByteArrayInputStream bis =null;
        ObjectInputStream ois = null;
        Object object = null;
        //转换对象
        bis = new ByteArrayInputStream(EncodesUtil.decodeBase64(str));
        try {
            ois = new ObjectInputStream(bis);
            object = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("流读取异常：{}",e);
        }finally {
            //关闭流
            try {
                bis.close();
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
                log.error("流读取异常：{}",e);
            }

        }
        return object;
    }
}
