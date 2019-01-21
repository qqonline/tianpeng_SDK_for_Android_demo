package com.tianpeng.tp_adsdk.admobhttp;

import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class SerializableCookie implements Serializable {
    private static final long serialVersionUID = 6374381828722046732L;
    private final transient Cookie cookie;
    private transient BasicClientCookie clientCookie;

    public SerializableCookie(Cookie var1) {
        this.cookie = var1;
    }

    public Cookie getCookie() {
        Object var1 = this.cookie;
        if (this.clientCookie != null) {
            var1 = this.clientCookie;
        }

        return (Cookie)var1;
    }

    private void writeObject(ObjectOutputStream var1) throws IOException {
        var1.writeObject(this.cookie.getName());
        var1.writeObject(this.cookie.getValue());
        var1.writeObject(this.cookie.getComment());
        var1.writeObject(this.cookie.getDomain());
        var1.writeObject(this.cookie.getExpiryDate());
        var1.writeObject(this.cookie.getPath());
        var1.writeInt(this.cookie.getVersion());
        var1.writeBoolean(this.cookie.isSecure());
    }

    private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
        String var2 = (String)var1.readObject();
        String var3 = (String)var1.readObject();
        this.clientCookie = new BasicClientCookie(var2, var3);
        this.clientCookie.setComment((String)var1.readObject());
        this.clientCookie.setDomain((String)var1.readObject());
        this.clientCookie.setExpiryDate((Date)var1.readObject());
        this.clientCookie.setPath((String)var1.readObject());
        this.clientCookie.setVersion(var1.readInt());
        this.clientCookie.setSecure(var1.readBoolean());
    }
}

