package com.sohuvideo.ui_plugin.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;


public class SohuProduce implements Serializable, Parcelable  {
    private int  is_album;
    private int is_trailer;
    private long aid;
    private String album_name;
    private String album_firstname;
    private String album_sub_name;
    private String album_desc;
    private String recommend_tip;
    private String search_name;
    private int cid;
    private String cate_code;

    private int is_sohuown;
    private int is_original_code;
    private int is_super_code;

    private String ver_high_pic;
    private String hor_high_pic;
    private String hor_w8_pic;
    private String hor_pic;
    private String ver_pic;
    private int update_status;
    private String updateNotification;
    private String tip;
    private int latest_video_count;
    private int total_video_count;
    private String year;
    private long area_id;
    private int language_id;
    private double score;
    private long play_count;
    private String director;
    private String main_actor;
    private String presenter;
    private String guest;
    private String update_time; // 更新日期
    private String time_length;
    private long vid;
    private String video_name;
    private String video_sub_name;
    private String video_desc;


    protected SohuProduce(Parcel in) {
        is_album = in.readInt();
        is_trailer = in.readInt();
        aid = in.readLong();
        album_name = in.readString();
        album_firstname = in.readString();
        album_sub_name = in.readString();
        album_desc = in.readString();
        recommend_tip = in.readString();
        search_name = in.readString();
        cid = in.readInt();
        cate_code = in.readString();
        is_sohuown = in.readInt();
        is_original_code = in.readInt();
        is_super_code = in.readInt();
        ver_high_pic = in.readString();
        hor_high_pic = in.readString();
        hor_w8_pic = in.readString();
        hor_pic = in.readString();
        ver_pic = in.readString();
        update_status = in.readInt();
        updateNotification = in.readString();
        tip = in.readString();
        latest_video_count = in.readInt();
        total_video_count = in.readInt();
        year = in.readString();
        area_id = in.readLong();
        language_id = in.readInt();
        score = in.readDouble();
        play_count = in.readLong();
        director = in.readString();
        main_actor = in.readString();
        presenter = in.readString();
        guest = in.readString();
        update_time = in.readString();
        time_length = in.readString();
        vid = in.readLong();
        video_name = in.readString();
        video_sub_name = in.readString();
        video_desc = in.readString();
    }

    public static final Creator<SohuProduce> CREATOR = new Creator<SohuProduce>() {
        @Override
        public SohuProduce createFromParcel(Parcel in) {
            return new SohuProduce(in);
        }

        @Override
        public SohuProduce[] newArray(int size) {
            return new SohuProduce[size];
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
        dest.writeString(album_name);
        dest.writeString(album_firstname);
        dest.writeString(album_sub_name);
        dest.writeString(album_desc);
        dest.writeString(recommend_tip);
        dest.writeString(search_name);
        dest.writeInt(cid);
        dest.writeString(cate_code);
        dest.writeInt(is_sohuown);
        dest.writeInt(is_original_code);
        dest.writeInt(is_super_code);
        dest.writeString(ver_high_pic);
        dest.writeString(hor_high_pic);
        dest.writeString(hor_w8_pic);
        dest.writeString(hor_pic);
        dest.writeString(ver_pic);
        dest.writeInt(update_status);
        dest.writeString(updateNotification);
        dest.writeString(tip);
        dest.writeInt(latest_video_count);
        dest.writeInt(total_video_count);
        dest.writeString(year);
        dest.writeLong(area_id);
        dest.writeInt(language_id);
        dest.writeDouble(score);
        dest.writeLong(play_count);
        dest.writeString(director);
        dest.writeString(main_actor);
        dest.writeString(presenter);
        dest.writeString(guest);
        dest.writeString(update_time);
        dest.writeString(time_length);
        dest.writeLong(vid);
        dest.writeString(video_name);
        dest.writeString(video_sub_name);
        dest.writeString(video_desc);
    }

    public String getCate_code() {
        return cate_code;
    }

    public void setCate_code(String cate_code) {
        this.cate_code = cate_code;
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

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public String getAlbum_firstname() {
        return album_firstname;
    }

    public void setAlbum_firstname(String album_firstname) {
        this.album_firstname = album_firstname;
    }

    public String getAlbum_sub_name() {
        return album_sub_name;
    }

    public void setAlbum_sub_name(String album_sub_name) {
        this.album_sub_name = album_sub_name;
    }

    public String getAlbum_desc() {
        return album_desc;
    }

    public void setAlbum_desc(String album_desc) {
        this.album_desc = album_desc;
    }

    public String getRecommend_tip() {
        return recommend_tip;
    }

    public void setRecommend_tip(String recommend_tip) {
        this.recommend_tip = recommend_tip;
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

    public int getIs_sohuown() {
        return is_sohuown;
    }

    public void setIs_sohuown(int is_sohuown) {
        this.is_sohuown = is_sohuown;
    }

    public int getIs_original_code() {
        return is_original_code;
    }

    public void setIs_original_code(int is_original_code) {
        this.is_original_code = is_original_code;
    }

    public int getIs_super_code() {
        return is_super_code;
    }

    public void setIs_super_code(int is_super_code) {
        this.is_super_code = is_super_code;
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

    public String getVer_pic() {
        return ver_pic;
    }

    public void setVer_pic(String ver_pic) {
        this.ver_pic = ver_pic;
    }

    public int getUpdate_status() {
        return update_status;
    }

    public void setUpdate_status(int update_status) {
        this.update_status = update_status;
    }

    public String getUpdateNotification() {
        return updateNotification;
    }

    public void setUpdateNotification(String updateNotification) {
        this.updateNotification = updateNotification;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public int getLatest_video_count() {
        return latest_video_count;
    }

    public void setLatest_video_count(int latest_video_count) {
        this.latest_video_count = latest_video_count;
    }

    public int getTotal_video_count() {
        return total_video_count;
    }

    public void setTotal_video_count(int total_video_count) {
        this.total_video_count = total_video_count;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public long getArea_id() {
        return area_id;
    }

    public void setArea_id(long area_id) {
        this.area_id = area_id;
    }

    public int getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(int language_id) {
        this.language_id = language_id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public long getPlay_count() {
        return play_count;
    }

    public void setPlay_count(long play_count) {
        this.play_count = play_count;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getMain_actor() {
        return main_actor;
    }

    public void setMain_actor(String main_actor) {
        this.main_actor = main_actor;
    }

    public String getPresenter() {
        return presenter;
    }

    public void setPresenter(String presenter) {
        this.presenter = presenter;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getTime_length() {
        return time_length;
    }

    public void setTime_length(String time_length) {
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

    public String getVideo_sub_name() {
        return video_sub_name;
    }

    public void setVideo_sub_name(String video_sub_name) {
        this.video_sub_name = video_sub_name;
    }

    public String getVideo_desc() {
        return video_desc;
    }

    public void setVideo_desc(String video_desc) {
        this.video_desc = video_desc;
    }

    public static Creator<SohuProduce> getCREATOR() {
        return CREATOR;
    }
}
