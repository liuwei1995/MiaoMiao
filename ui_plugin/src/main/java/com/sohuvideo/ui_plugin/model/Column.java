package com.sohuvideo.ui_plugin.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;


public class Column  implements Serializable, Parcelable  {

    private long vid;
    private long aid;
    private String video_name;
    private String album_name;
    private int site;
    private String hor_high_pic;
    private String ver_high_pic;
    private String hor_w8_pic;
    private long time_length;
    private long play_count;
    private boolean isShow = true;


    public  Column(){

    }

    protected Column(Parcel in) {
        vid = in.readLong();
        aid = in.readLong();
        video_name = in.readString();
        album_name = in.readString();
        site = in.readInt();
        hor_high_pic = in.readString();
        ver_high_pic = in.readString();
        hor_w8_pic = in.readString();
        time_length = in.readLong();
        play_count = in.readLong();
        isShow = in.readByte() != 0;
    }

    public static final Creator<Column> CREATOR = new Creator<Column>() {
        @Override
        public Column createFromParcel(Parcel in) {
            return new Column(in);
        }

        @Override
        public Column[] newArray(int size) {
            return new Column[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(vid);
        dest.writeLong(aid);
        dest.writeString(video_name);
        dest.writeString(album_name);
        dest.writeInt(site);
        dest.writeString(hor_high_pic);
        dest.writeString(ver_high_pic);
        dest.writeString(hor_w8_pic);
        dest.writeLong(time_length);
        dest.writeLong(play_count);
        dest.writeByte((byte) (isShow ? 1 : 0));
    }

    public long getVid() {
        return vid;
    }

    public void setVid(long vid) {
        this.vid = vid;
    }

    public long getAid() {
        return aid;
    }

    public void setAid(long aid) {
        this.aid = aid;
    }

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public int getSite() {
        return site;
    }

    public void setSite(int site) {
        this.site = site;
    }

    public String getHor_high_pic() {
        return hor_high_pic;
    }

    public void setHor_high_pic(String hor_high_pic) {
        this.hor_high_pic = hor_high_pic;
    }

    public String getVer_high_pic() {
        return ver_high_pic;
    }

    public void setVer_high_pic(String ver_high_pic) {
        this.ver_high_pic = ver_high_pic;
    }

    public String getHor_w8_pic() {
        return hor_w8_pic;
    }

    public void setHor_w8_pic(String hor_w8_pic) {
        this.hor_w8_pic = hor_w8_pic;
    }

    public long getTime_length() {
        return time_length;
    }

    public void setTime_length(long time_length) {
        this.time_length = time_length;
    }

    public long getPlay_count() {
        return play_count;
    }

    public void setPlay_count(long play_count) {
        this.play_count = play_count;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public static Creator<Column> getCREATOR() {
        return CREATOR;
    }
}
