package com.tianpeng.tp_adsdk.mine.bean;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class Status {
    private boolean foreground;
    private boolean upLoadOver;
    private long progressSize;
    private long newModified;

    public boolean isForeground() {
        return foreground;
    }

    public void setForeground(boolean foreground) {
        this.foreground = foreground;
    }

    public boolean isUpLoadOver() {
        return upLoadOver;
    }

    public void setUpLoadOver(boolean upLoadOver) {
        this.upLoadOver = upLoadOver;
    }

    public long getProgressSize() {
        return progressSize;
    }

    public void setProgressSize(long progressSize) {
        this.progressSize = progressSize;
    }

    public long getNewModified() {
        return newModified;
    }

    public void setNewModified(long newModified) {
        this.newModified = newModified;
    }

    public boolean isUploadOver(boolean foreground, long lastModified, long size) {
        if (!this.isForeground() && foreground) {
            this.modifyData(foreground, lastModified, size);
            return true;
        } else if (lastModified > this.getNewModified()) {
            this.modifyData(foreground, lastModified, size);
            return true;
        } else if (this.upLoadOver && size - this.getProgressSize() > 0L) {
            this.upLoadOver = false;
            this.modifyData(foreground, lastModified, size);
            return true;
        } else {
            this.modifyData(foreground, lastModified, size);
            return false;
        }
    }

    private void modifyData(boolean foreground, long lastModified, long size) {
        this.upLoadOver = size - this.getProgressSize() < 0L;
        this.setForeground(foreground);
        this.setNewModified(lastModified);
        this.setProgressSize(size);
    }
}
