package com.sohuvideo.ui_plugin.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;


public class Pgc  implements Serializable, Parcelable {

    private int is_album;
    private int is_trailer;
    private long aid;
    private String search_name;
    private int cid;
    private String cate_code;
    private int is_original_code;
    private String ver_high_pic;
    private String hor_high_pic;
    private String hor_w8_pic;
    private String hor_pic;
    private String tip;
    private int year;
    private String area_id;
    private long play_count;
    private String update_time;
    private int time_length;
    private long vid;
    private String video_name;
    private String video_desc;
    private int is_download;
    private int site;
    private String album_name;


    protected Pgc(Parcel in) {
        is_album = in.readInt();
        is_trailer = in.readInt();
        aid = in.readLong();
        search_name = in.readString();
        cid = in.readInt();
        cate_code = in.readString();
        is_original_code = in.readInt();
        ver_high_pic = in.readString();
        hor_high_pic = in.readString();
        hor_w8_pic = in.readString();
        hor_pic = in.readString();
        tip = in.readString();
        year = in.readInt();
        area_id = in.readString();
        play_count = in.readLong();
        update_time = in.readString();
        time_length = in.readInt();
        vid = in.readLong();
        video_name = in.readString();
        video_desc = in.readString();
        is_download = in.readInt();
        site = in.readInt();
        album_name = in.readString();
    }

    public static final Creator<Pgc> CREATOR = new Creator<Pgc>() {
        @Override
        public Pgc createFromParcel(Parcel in) {
            return new Pgc(in);
        }

        @Override
        public Pgc[] newArray(int size) {
            return new Pgc[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(is_album);
        dest.writeInt(is_trailer);
        dest.writeLong(aid);
        dest.writeString(search_name);
        dest.writeInt(cid);
        dest.writeString(cate_code);
        dest.writeInt(is_original_code);
        dest.writeString(ver_high_pic);
        dest.writeString(hor_high_pic);
        dest.writeString(hor_w8_pic);
        dest.writeString(hor_pic);
        dest.writeString(tip);
        dest.writeInt(year);
        dest.writeString(area_id);
        dest.writeLong(play_count);
        dest.writeString(update_time);
        dest.writeInt(time_length);
        dest.writeLong(vid);
        dest.writeString(video_name);
        dest.writeString(video_desc);
        dest.writeInt(is_download);
        dest.writeInt(site);
        dest.writeString(album_name);
    }

    public int getIs_album() {
        return is_album;
    }

    public void setIs_album(int is_album) {
        this.is_album = is_album;
    }

    public int getIs_trailer() {
        return is_trailer;
    }

    public void setIs_trailer(int is_trailer) {
        this.is_trailer = is_trailer;
    }

    public long getAid() {
        return aid;
    }

    public void setAid(long aid) {
        this.aid = aid;
    }

    public String getSearch_name() {
        return search_name;
    }

    public void setSearch_name(String search_name) {
        this.search_name = search_name;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getCate_code() {
        return cate_code;
    }

    public void setCate_code(String cate_code) {
        this.cate_code = cate_code;
    }

    public int getIs_original_code() {
        return is_original_code;
    }

    public void setIs_original_code(int is_original_code) {
        this.is_original_code = is_original_code;
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

    public String getHor_pic() {
        return hor_pic;
    }

    public void setHor_pic(String hor_pic) {
        this.hor_pic = hor_pic;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public long getPlay_count() {
        return play_count;
    }

    public void setPlay_count(long play_count) {
        this.play_count = play_count;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public int getTime_length() {
        return time_length;
    }

    public void setTime_length(int time_length) {
        this.time_length = time_length;
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

    public String getVideo_desc() {
        return video_desc;
    }

    public void setVideo_desc(String video_desc) {
        this.video_desc = video_desc;
    }

    public int getIs_download() {
        return is_download;
    }

    public void setIs_download(int is_download) {
        this.is_download = is_download;
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

    public static Creator<Pgc> getCREATOR() {
        return CREATOR;
    }
}
