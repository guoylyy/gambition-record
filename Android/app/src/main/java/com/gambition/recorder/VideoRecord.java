package com.gambition.recorder;

import java.util.Date;

public class VideoRecord {
    private String name;
    private Date date;
    private long second;

    public VideoRecord(String name, Date date, long second) {
        this.name = name;
        this.date = date;
        this.second = second;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getSecond() {
        return second;
    }

    public void setSecond(long second) {
        this.second = second;
    }
}
