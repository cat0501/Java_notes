package com.bs.basic.jvm;

/**
 * @author cat
 * @description
 * @date 2022/6/5 下午6:11
 */
public class HeapTest {
    public static void main(String[] args) {
        // Java虚拟机中的堆内存容量
        long initialMemory = Runtime.getRuntime().totalMemory() / 1024 / 1024;
        // Java虚拟机中的最大堆内存容量
        long maxMemory = Runtime.getRuntime().maxMemory() / 1024 / 1024;

        System.out.println("-Xms: " + initialMemory + "M");
        System.out.println("-Xmx: " + maxMemory + "M");

        System.out.println("系统内存大小为：" + initialMemory * 64.0 / 1024 + "G");
        System.out.println("系统内存大小为：" + maxMemory * 4.0 / 1024 + "G");

    }
}
