package com.sohuvideo.ui_plugin.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by tinghaoma on 2015/12/22.
 * 出品人专辑
 */
public class ProducerAlbumData  implements Serializable, Parcelable {

    private long aid;
    private int cid;
    private String album_name;
    private String cate_code;
    private String ver_big_pic;
    private String hor_big_pic;
    private String ver_high_pic;
    private String hor_high_pic;
    private String hor_w16_pic;
    private String hor_w6_pic;
    private String ver_w12_pic;
    private long vid;
    private String video_name;
    private String publish_time;
    private int site;
    private int data_type;


    protected ProducerAlbumData(Parcel in) {
        aid = in.readLong();
        cid = in.readInt();
        album_name = in.readString();
        cate_code = in.readString();
        ver_big_pic = in.readString();
        hor_big_pic = in.readString();
        ver_high_pic = in.readString();
        hor_high_pic = in.readString();
        hor_w16_pic = in.readString();
        hor_w6_pic = in.readString();
        ver_w12_pic = in.readString();
        vid = in.readLong();
        video_name = in.readString();
        publish_time = in.readString();
        site = in.readInt();
        data_type = in.readInt();
    }

    public static final Creator<ProducerAlbumData> CREATOR = new Creator<ProducerAlbumData>() {
        @Override
        public ProducerAlbumData createFromParcel(Parcel in) {
            return new ProducerAlbumData(in);
        }

        @Override
        public ProducerAlbumData[] newArray(int size) {
            return new ProducerAlbumData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(aid);
        dest.writeInt(cid);
        dest.writeString(album_name);
        dest.writeString(cate_code);
        dest.writeString(ver_big_pic);
        dest.writeString(hor_big_pic);
        dest.writeString(ver_high_pic);
        dest.writeString(hor_high_pic);
        dest.writeString(hor_w16_pic);
        dest.writeString(hor_w6_pic);
        dest.writeString(ver_w12_pic);
        dest.writeLong(vid);
        dest.writeString(video_name);
        dest.writeString(publish_time);
        dest.writeInt(site);
        dest.writeInt(data_type);
    }

    public long getAid() {
        return aid;
    }

    public void setAid(long aid) {
        this.aid = aid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
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

    public String getHor_w16_pic() {
        return hor_w16_pic;
    }

    public void setHor_w16_pic(String hor_w16_pic) {
        this.hor_w16_pic = hor_w16_pic;
    }

    public String getHor_w6_pic() {
        return hor_w6_pic;
    }

    public void setHor_w6_pic(String hor_w6_pic) {
        this.hor_w6_pic = hor_w6_pic;
    }

    public String getVer_w12_pic() {
        return ver_w12_pic;
    }

    public void setVer_w12_pic(String ver_w12_pic) {
        this.ver_w12_pic = ver_w12_pic;
    }

    public long getVid() {
        return vid;
    }

    public void setVid(long vid) {
        this.vid = vid;
    }

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    public String getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public int getSite() {
        return site;
    }

    public void setSite(int site) {
        this.site = site;
    }

    public int getData_type() {
        return data_type;
    }

    public void setData_type(int data_type) {
        this.data_type = data_type;
    }

    @Override
    public String toString() {
        return "ProducerAlbumData{" +
                "aid=" + aid +
                ", cid=" + cid +
                ", album_name='" + album_name + '\'' +
                ", cate_code='" + cate_code + '\'' +
                ", ver_big_pic='" + ver_big_pic + '\'' +
                ", hor_big_pic='" + hor_big_pic + '\'' +
                ", ver_high_pic='" + ver_high_pic + '\'' +
                ", hor_high_pic='" + hor_high_pic + '\'' +
                ", hor_w16_pic='" + hor_w16_pic + '\'' +
                ", hor_w6_pic='" + hor_w6_pic + '\'' +
                ", ver_w12_pic='" + ver_w12_pic + '\'' +
                ", vid=" + vid +
                ", video_name='" + video_name + '\'' +
                ", publish_time='" + publish_time + '\'' +
                ", site=" + site +
                ", data_type=" + data_type +
                '}';
    }
}
