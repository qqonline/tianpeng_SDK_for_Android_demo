package com.tianpeng.tp_adsdk.mine.bean;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class MacPacBean {
        private String packageName;
        private long machineId;
        private String startTime;

        public MacPacBean() {
        }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public long getMachineId() {
        return machineId;
    }

    public void setMachineId(long machineId) {
        this.machineId = machineId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
