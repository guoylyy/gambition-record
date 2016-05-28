package com.gambition.recorder;

import org.litepal.crud.DataSupport;

public class VideoRecord extends DataSupport {
    private String name;
    private long date;
    private long duration;
    private String path;

    public VideoRecord(String name, long date, long duration, String path) {
        this.name = name;
        this.date = date;
        this.duration = duration;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
