package com.tianpeng.tp_adsdk.mine.http;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class Constant {
    public static String BASEHTTPURL = "https://adtestapi.tianpengnet.cn";
    public static String BDLOACATION = "http://api.map.baidu.com/location/ip?ak=puMFpjydF1VjOWp9xkyGHioMMd6gqlqD&coor=bd09ll";

    public String clickDataUrl;
    public String uploadStatus;
    public String crashDataUrl;
    public String installDataUrl;
    public String downloadDataUrl;
    public String getADConfig;

    public Constant(){
        this.uploadStatus = BASEHTTPURL + "/ad/sdk/report";
        this.clickDataUrl = BASEHTTPURL + "/sdk/report";
        this.crashDataUrl = BASEHTTPURL + "/sdk/report";
        this.installDataUrl = BASEHTTPURL + "/sdk/report";
        this.downloadDataUrl = BASEHTTPURL + "/sdk/report";
        this.getADConfig = BASEHTTPURL + "/ad/sdk/route";
    }

}
