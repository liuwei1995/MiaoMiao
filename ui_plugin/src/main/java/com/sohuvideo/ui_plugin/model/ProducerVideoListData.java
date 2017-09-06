package com.sohuvideo.ui_plugin.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tinghaoma on 2015/12/22.
 */
public class ProducerVideoListData  implements Serializable, Parcelable {
    private int count;
    private List<ProducerVideoData> videos;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ProducerVideoData> getVideos() {
        return videos;
    }

    public void setVideos(List<ProducerVideoData> videos) {
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

    public ProducerVideoListData() {
    }

    protected ProducerVideoListData(Parcel in) {
        this.count = in.readInt();
        this.videos = in.createTypedArrayList(ProducerVideoData.CREATOR);
    }

    public static final Creator<ProducerVideoListData> CREATOR = new Creator<ProducerVideoListData>() {
        @Override
        public ProducerVideoListData createFromParcel(Parcel source) {
            return new ProducerVideoListData(source);
        }

        @Override
        public ProducerVideoListData[] newArray(int size) {
            return new ProducerVideoListData[size];
        }
    };
}
