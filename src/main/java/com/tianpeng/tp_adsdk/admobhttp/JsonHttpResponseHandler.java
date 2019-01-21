package com.tianpeng.tp_adsdk.admobhttp;

import android.os.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


/**
 * Created by YuHong on 2019/1/4 0004.
 */
public class JsonHttpResponseHandler extends AsyncHttpResponseHandler {
    protected static final int SUCCESS_JSON_MESSAGE = 100;

    public JsonHttpResponseHandler() {
    }

    public void onSuccess(JSONObject var1) {
    }

    public void onSuccess(JSONArray var1) {
    }

    public void onSuccess(int var1, JSONObject var2) {
        this.onSuccess(var2);
    }

    public void onSuccess(int var1, JSONArray var2) {
        this.onSuccess(var2);
    }

    public void onFailure(Throwable var1, JSONObject var2) {
    }

    public void onFailure(Throwable var1, JSONArray var2) {
    }

    protected void sendSuccessMessage(int var1, String var2) {
        if (var1 != 204) {
            try {
                Object var3 = this.parseResponse(var2);
                this.sendMessage(this.obtainMessage(100, new Object[]{var1, var3}));
            } catch (JSONException var4) {
                this.sendFailureMessage(var4, var2);
            }
        } else {
            this.sendMessage(this.obtainMessage(100, new Object[]{var1, new JSONObject()}));
        }

    }

    protected void handleMessage(Message var1) {
        switch(var1.what) {
            case 100:
                Object[] var2 = (Object[])((Object[])var1.obj);
                this.handleSuccessJsonMessage((Integer)var2[0], var2[1]);
                break;
            default:
                super.handleMessage(var1);
        }

    }

    protected void handleSuccessJsonMessage(int var1, Object var2) {
        if (var2 instanceof JSONObject) {
            this.onSuccess(var1, (JSONObject)var2);
        } else if (var2 instanceof JSONArray) {
            this.onSuccess(var1, (JSONArray)var2);
        } else {
            this.onFailure(new JSONException("Unexpected type " + var2.getClass().getName()), (JSONObject)((JSONObject)null));
        }

    }

    protected Object parseResponse(String var1) throws JSONException {
        Object var2 = null;
        var1 = var1.trim();
        if (var1.startsWith("{") || var1.startsWith("[")) {
            var2 = (new JSONTokener(var1)).nextValue();
        }

        if (var2 == null) {
            var2 = var1;
        }

        return var2;
    }

    protected void handleFailureMessage(Throwable var1, String var2) {
        try {
            if (var2 != null) {
                Object var3 = this.parseResponse(var2);
                if (var3 instanceof JSONObject) {
                    this.onFailure(var1, (JSONObject)var3);
                } else if (var3 instanceof JSONArray) {
                    this.onFailure(var1, (JSONArray)var3);
                } else {
                    this.onFailure(var1, (String)var2);
                }
            } else {
                this.onFailure(var1, (String)"");
            }
        } catch (JSONException var4) {
            this.onFailure(var1, (String)var2);
        }

    }
}
