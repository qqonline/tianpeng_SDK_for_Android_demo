package com.tianpeng.tp_adsdk.mine.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class ShellUtils {
    public static final String COMMAND_SU = "su";
    public static final String COMMAND_SH = "sh";
    public static final String COMMAND_EXIT = "exit\n";
    public static final String COMMAND_LINE_END = "\n";

    private ShellUtils() {
        throw new AssertionError();
    }

    public static boolean checkRootPermission() {
        return execCommand("echo root", true, false).result == 0;
    }

    public static ShellUtils.CommandResult execCommand(String var0, boolean var1) {
        return execCommand(new String[]{var0}, var1, true);
    }

    public static ShellUtils.CommandResult execCommand(String[] var0, boolean var1) {
        return execCommand(var0, var1, true);
    }

    public static ShellUtils.CommandResult execCommand(String var0, boolean var1, boolean var2) {
        return execCommand(new String[]{var0}, var1, var2);
    }

    public static ShellUtils.CommandResult execCommand(String[] var0, boolean var1, boolean var2) {
        int var3 = -1;
        if (var0 != null && var0.length != 0) {
            Process var4 = null;
            BufferedReader var5 = null;
            BufferedReader var6 = null;
            StringBuilder var7 = null;
            StringBuilder var8 = null;
            DataOutputStream var9 = null;

            try {
                var4 = Runtime.getRuntime().exec(var1 ? "su" : "sh");
                var9 = new DataOutputStream(var4.getOutputStream());
                String[] var10 = var0;
                int var11 = var0.length;

                for(int var12 = 0; var12 < var11; ++var12) {
                    String var13 = var10[var12];
                    if (var13 != null) {
                        var9.write(var13.getBytes());
                        var9.writeBytes("\n");
                        var9.flush();
                    }
                }

                var9.writeBytes("exit\n");
                var9.flush();
                var3 = var4.waitFor();
                if (var2) {
                    var7 = new StringBuilder();
                    var8 = new StringBuilder();
                    var5 = new BufferedReader(new InputStreamReader(var4.getInputStream()));
                    var6 = new BufferedReader(new InputStreamReader(var4.getErrorStream()));

                    String var27;
                    while((var27 = var5.readLine()) != null) {
                        var7.append(var27);
                    }

                    while((var27 = var6.readLine()) != null) {
                        var8.append(var27);
                    }
                }
            } catch (IOException var24) {
                ;
            } catch (Exception var25) {
                ;
            } finally {
                try {
                    if (var9 != null) {
                        var9.close();
                    }

                    if (var5 != null) {
                        var5.close();
                    }

                    if (var6 != null) {
                        var6.close();
                    }
                } catch (IOException var23) {
                    ;
                }

                if (var4 != null) {
                    var4.destroy();
                }

            }

            return new ShellUtils.CommandResult(var3, var7 == null ? null : var7.toString(), var8 == null ? null : var8.toString());
        } else {
            return new ShellUtils.CommandResult(var3, (String)null, (String)null);
        }
    }

    public static class CommandResult {
        public int result;
        public String successMsg;
        public String errorMsg;

        public CommandResult(int var1) {
            this.result = var1;
        }

        public CommandResult(int var1, String var2, String var3) {
            this.result = var1;
            this.successMsg = var2;
            this.errorMsg = var3;
        }
    }
}

