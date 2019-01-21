package com.tianpeng.tp_adsdk.mine.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class PhoneNumberMatchUtils {
    public PhoneNumberMatchUtils() {
    }

    public static boolean schemeMatch(String var0) {
        Pattern var1 = Pattern.compile("^(?!http)[0-9a-zA-Z]{2,}:\\/\\/[\\d\\D]*");
        Matcher var2 = var1.matcher(var0);
        return var2.matches();
    }

    public static String getMatcher(String var0) {
        String var1 = "";
        Pattern var2 = Pattern.compile("http[s]?:\\/\\/[^\"]*");

        for(Matcher var3 = var2.matcher(var0); var3.find(); var1 = var3.group(0)) {
            ;
        }

        return var1;
    }
}

