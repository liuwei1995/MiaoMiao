package com.sohuvideo.ui_plugin.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by shanli208820 on 2015/11/27.
 */
public class Video  implements Serializable, Parcelable {

    private long vid;
    private long aid;
    private int site;
    private String video_name;

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

    public int getSite() {
        return site;
    }

    public void setSite(int site) {
        this.site = site;
    }

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    @Override
    public String toString() {
        return "Video{" +
                "vid=" + vid +
                ", aid=" + aid +
                ", site=" + site +
                ", video_name='" + video_name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.vid);
        dest.writeLong(this.aid);
        dest.writeInt(this.site);
        dest.writeString(this.video_name);
    }

    public Video() {
    }

    protected Video(Parcel in) {
        this.vid = in.readLong();
        this.aid = in.readLong();
        this.site = in.readInt();
        this.video_name = in.readString();
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel source) {
            return new Video(source);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };
}
