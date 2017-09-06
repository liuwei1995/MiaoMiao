package com.sohuvideo.ui_plugin.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;


public class SoHuProduceListData implements Serializable, Parcelable {
    private int count;
    private List<SohuProduce> videos;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<SohuProduce> getVideos() {
        return videos;
    }

    public void setVideos(List<SohuProduce> videos) {
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

    public SoHuProduceListData() {
    }

    protected SoHuProduceListData(Parcel in) {
        this.count = in.readInt();
        this.videos = in.createTypedArrayList(SohuProduce.CREATOR);
    }

    public static final Creator<SoHuProduceListData> CREATOR = new Creator<SoHuProduceListData>() {
        @Override
        public SoHuProduceListData createFromParcel(Parcel source) {
            return new SoHuProduceListData(source);
        }

        @Override
        public SoHuProduceListData[] newArray(int size) {
            return new SoHuProduceListData[size];
        }
    };
}
