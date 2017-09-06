package com.sohuvideo.ui_plugin.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class PgcListData  implements Serializable, Parcelable {

    private int count;
    private List<Pgc> videos;


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Pgc> getVideos() {
        return videos;
    }

    public void setVideos(List<Pgc> videos) {
        this.videos = videos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.count);
        dest.writeTypedList(this.videos);
    }

    public PgcListData() {
    }

    protected PgcListData(Parcel in) {
        this.count = in.readInt();
        this.videos = in.createTypedArrayList(Pgc.CREATOR);
    }

    public static final Creator<PgcListData> CREATOR = new Creator<PgcListData>() {
        @Override
        public PgcListData createFromParcel(Parcel source) {
            return new PgcListData(source);
        }

        @Override
        public PgcListData[] newArray(int size) {
            return new PgcListData[size];
        }
    };
}
