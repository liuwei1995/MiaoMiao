package com.sohuvideo.ui_plugin.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shanli208820 on 2015/11/27.
 */
public class AlbumListData implements Serializable, Parcelable {

    private int count;
    private List<Video> videos;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.count);
        dest.writeList(this.videos);
    }

    public AlbumListData() {
    }

    protected AlbumListData(Parcel in) {
        this.count = in.readInt();
        this.videos = new ArrayList<Video>();
        in.readList(this.videos, Video.class.getClassLoader());
    }

    public static final Parcelable.Creator<AlbumListData> CREATOR = new Parcelable.Creator<AlbumListData>() {
        @Override
        public AlbumListData createFromParcel(Parcel source) {
            return new AlbumListData(source);
        }

        @Override
        public AlbumListData[] newArray(int size) {
            return new AlbumListData[size];
        }
    };
}
