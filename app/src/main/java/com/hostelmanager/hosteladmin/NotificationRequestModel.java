package com.hostelmanager.hosteladmin;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

/**
 * Created by sudha on 07-Apr-18.
 */

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class NotificationRequestModel {

    @SerializedName("data")
    private NotificationData mData;
    @SerializedName("to")
    private String mTo;

    public NotificationData getData() {
        return mData;
    }

    public void setData(NotificationData data) {
        mData = data;
    }

    public String getTo() {
        return mTo;
    }

    public void setTo(String to) {
        mTo = to;
    }
}