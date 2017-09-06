package com.sohuvideo.ui_plugin.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 出品人信息
 */
public class ProducerInfoData  implements Serializable, Parcelable {

    private int data_type;
    private long user_id;
    private String nickname;
    private String small_pic;
    private long total_video_count;
    private String total_video_count_tip;
    private long total_fans_count;
    private String total_fans_count_tip;
    private long total_play_count;
    private String total_play_count_tip;
    private int verified;
    private String bg_pic;
    private String url_html5;
    private String hor_url_html5;

    protected ProducerInfoData(Parcel in) {
        data_type = in.readInt();
        user_id = in.readLong();
        nickname = in.readString();
        small_pic = in.readString();
        total_video_count = in.readLong();
        total_video_count_tip = in.readString();
        total_fans_count = in.readLong();
        total_fans_count_tip = in.readString();
        total_play_count = in.readLong();
        total_play_count_tip = in.readString();
        verified = in.readInt();
        bg_pic = in.readString();
        url_html5 = in.readString();
        hor_url_html5 = in.readString();
    }

    public static final Creator<ProducerInfoData> CREATOR = new Creator<ProducerInfoData>() {
        @Override
        public ProducerInfoData createFromParcel(Parcel in) {
            return new ProducerInfoData(in);
        }

        @Override
        public ProducerInfoData[] newArray(int size) {
            return new ProducerInfoData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(data_type);
        dest.writeLong(user_id);
        dest.writeString(nickname);
        dest.writeString(small_pic);
        dest.writeLong(total_video_count);
        dest.writeString(total_video_count_tip);
        dest.writeLong(total_fans_count);
        dest.writeString(total_fans_count_tip);
        dest.writeLong(total_play_count);
        dest.writeString(total_play_count_tip);
        dest.writeInt(verified);
        dest.writeString(bg_pic);
        dest.writeString(url_html5);
        dest.writeString(hor_url_html5);
    }


    public int getData_type() {
        return data_type;
    }

    public void setData_type(int data_type) {
        this.data_type = data_type;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSmall_pic() {
        return small_pic;
    }

    public void setSmall_pic(String small_pic) {
        this.small_pic = small_pic;
    }

    public long getTotal_video_count() {
        return total_video_count;
    }

    public void setTotal_video_count(long total_video_count) {
        this.total_video_count = total_video_count;
    }

    public String getTotal_video_count_tip() {
        return total_video_count_tip;
    }

    public void setTotal_video_count_tip(String total_video_count_tip) {
        this.total_video_count_tip = total_video_count_tip;
    }

    public long getTotal_fans_count() {
        return total_fans_count;
    }

    public void setTotal_fans_count(long total_fans_count) {
        this.total_fans_count = total_fans_count;
    }

    public String getTotal_fans_count_tip() {
        return total_fans_count_tip;
    }

    public void setTotal_fans_count_tip(String total_fans_count_tip) {
        this.total_fans_count_tip = total_fans_count_tip;
    }

    public long getTotal_play_count() {
        return total_play_count;
    }

    public void setTotal_play_count(long total_play_count) {
        this.total_play_count = total_play_count;
    }

    public String getTotal_play_count_tip() {
        return total_play_count_tip;
    }

    public void setTotal_play_count_tip(String total_play_count_tip) {
        this.total_play_count_tip = total_play_count_tip;
    }

    public int getVerified() {
        return verified;
    }

    public void setVerified(int verified) {
        this.verified = verified;
    }

    public String getBg_pic() {
        return bg_pic;
    }

    public void setBg_pic(String bg_pic) {
        this.bg_pic = bg_pic;
    }

    public String getUrl_html5() {
        return url_html5;
    }

    public void setUrl_html5(String url_html5) {
        this.url_html5 = url_html5;
    }

    public String getHor_url_html5() {
        return hor_url_html5;
    }

    public void setHor_url_html5(String hor_url_html5) {
        this.hor_url_html5 = hor_url_html5;
    }
}
