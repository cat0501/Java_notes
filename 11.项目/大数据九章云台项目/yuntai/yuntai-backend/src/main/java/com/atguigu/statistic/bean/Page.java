package com.atguigu.statistic.bean;

import java.util.List;

public class Page<T> {
    public Integer total;
    public Integer size;
    public Integer current;
    public List<T> records;

    public Page() {
    }
}
