package com.tianpeng.tp_adsdk.mine.business;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class OperationBean {
    private String operationType;
    private int machineType;
    private String scheme;
    private String startCooX;
    private String endCooX;
    private String startCooY;
    private String endCooY;
    private String startTime;
    private String endTime;
    private String packageName;
    private String versionNo;
    private long machineId;

    public OperationBean() {
    }

    public int getMachineType() {
        return this.machineType;
    }

    public void setMachineType(int var1) {
        this.machineType = var1;
    }

    public String getOperationType() {
        return this.operationType;
    }

    public void setOperationType(String var1) {
        this.operationType = var1;
    }

    public String getScheme() {
        return this.scheme;
    }

    public void setScheme(String var1) {
        this.scheme = var1;
    }

    public String getStartCooX() {
        return this.startCooX;
    }

    public void setStartCooX(String var1) {
        this.startCooX = var1;
    }

    public String getEndCooX() {
        return this.endCooX;
    }

    public void setEndCooX(String var1) {
        this.endCooX = var1;
    }

    public String getStartCooY() {
        return this.startCooY;
    }

    public void setStartCooY(String var1) {
        this.startCooY = var1;
    }

    public String getEndCooY() {
        return this.endCooY;
    }

    public void setEndCooY(String var1) {
        this.endCooY = var1;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String var1) {
        this.startTime = var1;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String var1) {
        this.endTime = var1;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String var1) {
        this.packageName = var1;
    }

    public String getVersionNo() {
        return this.versionNo;
    }

    public void setVersionNo(String var1) {
        this.versionNo = var1;
    }

    public long getMachineId() {
        return this.machineId;
    }

    public void setMachineId(long var1) {
        this.machineId = var1;
    }

    public String toString() {
        return "OperationBean{operationType='" + this.operationType + '\'' + ", machineType=" + this.machineType + ", scheme='" + this.scheme + '\'' + ", startCooX='" + this.startCooX + '\'' + ", endCooX='" + this.endCooX + '\'' + ", startCooY='" + this.startCooY + '\'' + ", endCooY='" + this.endCooY + '\'' + ", startTime='" + this.startTime + '\'' + ", endTime='" + this.endTime + '\'' + ", packageName='" + this.packageName + '\'' + ", versionNo='" + this.versionNo + '\'' + ", machineId=" + this.machineId + '}';
    }
}
