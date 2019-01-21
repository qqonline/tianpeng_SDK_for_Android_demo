package com.tianpeng.tp_adsdk.admobhttp;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicHeader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
class SimpleMultipartEntity implements HttpEntity {
    private static final char[] MULTIPART_CHARS = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private String boundary = null;
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    boolean isSetLast = false;
    boolean isSetFirst = false;

    public SimpleMultipartEntity() {
        StringBuffer var1 = new StringBuffer();
        Random var2 = new Random();

        for(int var3 = 0; var3 < 30; ++var3) {
            var1.append(MULTIPART_CHARS[var2.nextInt(MULTIPART_CHARS.length)]);
        }

        this.boundary = var1.toString();
    }

    public void writeFirstBoundaryIfNeeds() {
        if (!this.isSetFirst) {
            try {
                this.out.write(("--" + this.boundary + "\r\n").getBytes());
            } catch (IOException var2) {
                var2.printStackTrace();
            }
        }

        this.isSetFirst = true;
    }

    public void writeLastBoundaryIfNeeds() {
        if (!this.isSetLast) {
            try {
                this.out.write(("\r\n--" + this.boundary + "--\r\n").getBytes());
            } catch (IOException var2) {
                var2.printStackTrace();
            }

            this.isSetLast = true;
        }
    }

    public void addPart(String var1, String var2) {
        this.writeFirstBoundaryIfNeeds();

        try {
            this.out.write(("Content-Disposition: form-data; name=\"" + var1 + "\"\r\n\r\n").getBytes());
            this.out.write(var2.getBytes());
            this.out.write(("\r\n--" + this.boundary + "\r\n").getBytes());
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }

    public void addPart(String var1, String var2, InputStream var3, boolean var4) {
        this.addPart(var1, var2, var3, "application/octet-stream", var4);
    }

    public void addPart(String var1, String var2, InputStream var3, String var4, boolean var5) {
        this.writeFirstBoundaryIfNeeds();

        try {
            var4 = "Content-Type: " + var4 + "\r\n";
            this.out.write(("Content-Disposition: form-data; name=\"" + var1 + "\"; filename=\"" + var2 + "\"\r\n").getBytes());
            this.out.write(var4.getBytes());
            this.out.write("Content-Transfer-Encoding: binary\r\n\r\n".getBytes());
            byte[] var6 = new byte[4096];
            boolean var7 = false;

            int var18;
            while((var18 = var3.read(var6)) != -1) {
                this.out.write(var6, 0, var18);
            }

            if (!var5) {
                this.out.write(("\r\n--" + this.boundary + "\r\n").getBytes());
            }

            this.out.flush();
        } catch (IOException var16) {
            var16.printStackTrace();
        } finally {
            try {
                var3.close();
            } catch (IOException var15) {
                var15.printStackTrace();
            }

        }

    }

    public void addPart(String var1, File var2, boolean var3) {
        try {
            this.addPart(var1, var2.getName(), new FileInputStream(var2), var3);
        } catch (FileNotFoundException var5) {
            var5.printStackTrace();
        }

    }

    public long getContentLength() {
        this.writeLastBoundaryIfNeeds();
        return (long)this.out.toByteArray().length;
    }

    public Header getContentType() {
        return new BasicHeader("Content-Type", "multipart/form-data; boundary=" + this.boundary);
    }

    public boolean isChunked() {
        return false;
    }

    public boolean isRepeatable() {
        return false;
    }

    public boolean isStreaming() {
        return false;
    }

    public void writeTo(OutputStream var1) throws IOException {
        var1.write(this.out.toByteArray());
    }

    public Header getContentEncoding() {
        return null;
    }

    public void consumeContent() {
        if (this.isStreaming()) {
            throw new UnsupportedOperationException("Streaming entity does not implement #consumeContent()");
        }
    }

    public InputStream getContent() {
        return new ByteArrayInputStream(this.out.toByteArray());
    }
}

