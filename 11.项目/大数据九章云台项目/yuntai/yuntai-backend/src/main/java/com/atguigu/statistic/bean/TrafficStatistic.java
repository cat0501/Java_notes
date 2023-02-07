package com.atguigu.statistic.bean;

public class TrafficStatistic {
    private String dt;
    private Integer recentDays;
    private String channel;
    private Integer uvCount;
    private Integer avgDurationSec;
    private Integer avgPageCount;
    private Integer svCount;
    private Double bounceRate;

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public Integer getRecentDays() {
        return recentDays;
    }

    public void setRecentDays(Integer recentDays) {
        this.recentDays = recentDays;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Integer getUvCount() {
        return uvCount;
    }

    public void setUvCount(Integer uvCount) {
        this.uvCount = uvCount;
    }

    public Integer getAvgDurationSec() {
        return avgDurationSec;
    }

    public void setAvgDurationSec(Integer avgDurationSec) {
        this.avgDurationSec = avgDurationSec;
    }

    public Integer getAvgPageCount() {
        return avgPageCount;
    }

    public void setAvgPageCount(Integer avgPageCount) {
        this.avgPageCount = avgPageCount;
    }

    public Integer getSvCount() {
        return svCount;
    }

    public void setSvCount(Integer svCount) {
        this.svCount = svCount;
    }

    public Double getBounceRate() {
        return bounceRate;
    }

    public void setBounceRate(Double bounceRate) {
        this.bounceRate = bounceRate;
    }
}
