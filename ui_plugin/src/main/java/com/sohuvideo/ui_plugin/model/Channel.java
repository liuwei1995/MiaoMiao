package com.sohuvideo.ui_plugin.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.sohuvideo.ui_plugin.api.StatisticConstants;

import java.io.Serializable;

/**
 * 标题--新闻,原创,运动.....
 */
public class Channel  implements Serializable, Parcelable {

    private int cate_id;
    private int cate_code;
    private String cate_name;
    private int type= StatisticConstants.FragmentType.CHANNEL;

    public Channel(){

    }

    protected Channel(Parcel in) {
        cate_id = in.readInt();
        cate_code = in.readInt();
        cate_name = in.readString();
        type = in.readInt();
    }

    public static final Creator<Channel> CREATOR = new Creator<Channel>() {
        @Override
        public Channel createFromParcel(Parcel in) {
            return new Channel(in);
        }

        @Override
        public Channel[] newArray(int size) {
            return new Channel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cate_id);
        dest.writeInt(cate_code);
        dest.writeString(cate_name);
        dest.writeInt(type);
    }


    public int getCate_id() {
        return cate_id;
    }

    public void setCate_id(int cate_id) {
        this.cate_id = cate_id;
    }

    public int getCate_code() {
        return cate_code;
    }

    public void setCate_code(int cate_code) {
        this.cate_code = cate_code;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
