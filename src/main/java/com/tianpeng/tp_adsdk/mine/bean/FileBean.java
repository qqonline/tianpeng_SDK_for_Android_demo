package com.tianpeng.tp_adsdk.mine.bean;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class FileBean {
    private String filePath;
    private String packageName;
    private String fileName;

    public FileBean() {
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
