package com.example.xuchichi.mytest.model;

import java.io.Serializable;

/**
 * Created by xuchichi on 2018/3/24.
 * 文件实体类 --文件下载
 */

public class FileInfo implements Serializable{
    private int id;
    private String fileName;
    private String url;
    private int length;
    private int finished;

    public FileInfo() {
    }

    public FileInfo(int id, String fileName, String url, int length, int finished) {
        this.id = id;
        this.fileName = fileName;
        this.url = url;
        this.length = length;
        this.finished = finished;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", url='" + url + '\'' +
                ", length=" + length +
                ", finished=" + finished +
                '}';
    }
}
