package com.sohuvideo.ui_plugin.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by tinghaoma on 2015/12/23.
 */
public class ProducerVideoData   implements Serializable, Parcelable {
    private long aid;
    private long vid;
    private int site;
    private String album_name;
    private String cate_code;
    private String publish_time;
    private String video_name;
    private String ver_big_pic;
    private String hor_big_pic;
    private String ver_high_pic;
    private String hor_high_pic;
    private String hor_w8_pic;
    private long play_count;
    private int total_duration;
    private int data_type;


    protected ProducerVideoData(Parcel in) {
        aid = in.readLong();
        vid = in.readLong();
        site = in.readInt();
        album_name = in.readString();
        cate_code = in.readString();
        publish_time = in.readString();
        video_name = in.readString();
        ver_big_pic = in.readString();
        hor_big_pic = in.readString();
        ver_high_pic = in.readString();
        hor_high_pic = in.readString();
        hor_w8_pic = in.readString();
        play_count = in.readLong();
        total_duration = in.readInt();
        data_type = in.readInt();
    }

    public static final Creator<ProducerVideoData> CREATOR = new Creator<ProducerVideoData>() {
        @Override
        public ProducerVideoData createFromParcel(Parcel in) {
            return new ProducerVideoData(in);
        }

        @Override
        public ProducerVideoData[] newArray(int size) {
            return new ProducerVideoData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(aid);
        dest.writeLong(vid);
        dest.writeInt(site);
        dest.writeString(album_name);
        dest.writeString(cate_code);
        dest.writeString(publish_time);
        dest.writeString(video_name);
        dest.writeString(ver_big_pic);
        dest.writeString(hor_big_pic);
        dest.writeString(ver_high_pic);
        dest.writeString(hor_high_pic);
        dest.writeString(hor_w8_pic);
        dest.writeLong(play_count);
        dest.writeInt(total_duration);
        dest.writeInt(data_type);
    }


    public long getAid() {
        return aid;
    }

    public void setAid(long aid) {
        this.aid = aid;
    }

    public long getVid() {
        return vid;
    }

    public void setVid(long vid) {
        this.vid = vid;
    }

    public int getSite() {
        return site;
    }

    public void setSite(int site) {
        this.site = site;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public String getCate_code() {
        return cate_code;
    }

    public void setCate_code(String cate_code) {
        this.cate_code = cate_code;
    }

    public String getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    public String getVer_big_pic() {
        return ver_big_pic;
    }

    public void setVer_big_pic(String ver_big_pic) {
        this.ver_big_pic = ver_big_pic;
    }

    public String getHor_big_pic() {
        return hor_big_pic;
    }

    public void setHor_big_pic(String hor_big_pic) {
        this.hor_big_pic = hor_big_pic;
    }

    public String getVer_high_pic() {
        return ver_high_pic;
    }

    public void setVer_high_pic(String ver_high_pic) {
        this.ver_high_pic = ver_high_pic;
    }

    public String getHor_high_pic() {
        return hor_high_pic;
    }

    public void setHor_high_pic(String hor_high_pic) {
        this.hor_high_pic = hor_high_pic;
    }

    public String getHor_w8_pic() {
        return hor_w8_pic;
    }

    public void setHor_w8_pic(String hor_w8_pic) {
        this.hor_w8_pic = hor_w8_pic;
    }

    public long getPlay_count() {
        return play_count;
    }

    public void setPlay_count(long play_count) {
        this.play_count = play_count;
    }

    public int getTotal_duration() {
        return total_duration;
    }

    public void setTotal_duration(int total_duration) {
        this.total_duration = total_duration;
    }

    public int getData_type() {
        return data_type;
    }

    public void setData_type(int data_type) {
        this.data_type = data_type;
    }
}
