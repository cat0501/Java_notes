package com.itheima.shiro.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 序列生成工具（有序的）
 * <u>参考Twitter Snowflake算法（64位自增ID）</u>
 * <i>
 * 41bit时间戳（可使用约70年）
 * 10bit机器服务标识（可支持1024个服务）
 * 12bit序列号（1毫秒可产生4095个序列）
 * </i>
 **/
public class SeqGenerator {

    //Fri May 13 11:03:16 CST 2016|
    private final static long twepoch            = 1463108596098L;             //日期起始点

    private final long        workerId;
    private long              sequence           = 0L;
    private long              lastTimestamp      = -1L;

    private final static long workerIdBits       = 10L;                        //机器ID占用10bits
    private final static long sequenceBits       = 12L;                        //序列占用12bits

    public final static long  maxWorkerId        = -1L ^ -1L << workerIdBits;  //机器ID 最大值

    private final static long timestampLeftShift = sequenceBits + workerIdBits; //时间偏移位
    private final static long workerIdShift      = sequenceBits;               //机器ID偏移位

    public final static long  sequenceMask       = -1L ^ -1L << sequenceBits;  //序列掩码

    public SeqGenerator(final long workerId) {
        super();
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format(
                "worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        this.workerId = workerId;
    }

    public SeqGenerator() {
        this(initWorkId());
    }

    private static long initWorkId() {
        InetAddress inetAddress = null;
        Long workId = 0L;
        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        byte[] bytes = inetAddress.getAddress();
        for (byte v : bytes) {
            workId += v < 0 ? v + 256 : v;
        }
        return workId;
    }

    public synchronized long nextId() {
        long timestamp = this.timeGen();
        if (timestamp < this.lastTimestamp) {
            // 服务器时间比上次倒退，时间不准
            try {
                throw new Exception(String.format(
                    "Clock moved backwards.  Refusing to generate id for %d milliseconds",
                    this.lastTimestamp - timestamp));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 毫秒数在变化时，保证序列值也一直变，累计最大值后重新0开始
        this.sequence = (this.sequence + 1) & sequenceMask;
        this.lastTimestamp = timestamp;
        long nextId = ((timestamp - twepoch << timestampLeftShift))
                      | (this.workerId << workerIdShift) | (this.sequence);
        return nextId;
    }

    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        final Map<Long, Long> store = new ConcurrentHashMap<>();
        final int MAX_POOL_SIZE = 1;
        ExecutorService service = Executors.newFixedThreadPool(MAX_POOL_SIZE);
        SeqGenerator seqKit = new SeqGenerator(1);
        System.out.println("sequenceMask:::"+ sequenceMask);
        for (int i = 0; i < MAX_POOL_SIZE; i++) {
            final int x = i;
            service.execute(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 4097; j++) {
                        System.out.println("thread seq" + seqKit.sequence);
                        long id = seqKit.nextId();
                        if (store.containsKey(id)) {
                            System.out.println("dublicate:" + id);
                        } else {
                            store.put(id, id);
                            System.out.println(id);
                        }
                    }
                    System.out.println("thread" + x + " end.");
                }
            });
        }
        service.shutdown();
    }

}
