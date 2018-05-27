package com.hostelmanager.hosteladmin.Model;

/**
 * Created by sudha on 10-May-18.
 */

public class SendNotifi {
    private String msg,token,key;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public SendNotifi(String msg, String token, String key) {

        this.msg = msg;
        this.token = token;
        this.key = key;
    }

    public SendNotifi() {

    }
}
