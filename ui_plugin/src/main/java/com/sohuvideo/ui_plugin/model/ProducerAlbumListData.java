package com.sohuvideo.ui_plugin.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tinghaoma on 2015/12/22.
 */
public class ProducerAlbumListData  implements Serializable, Parcelable {

    private int count;
    private List<ProducerAlbumData> albums;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ProducerAlbumData> getAlbums() {
        return albums;
    }

    public void setAlbums(List<ProducerAlbumData> albums) {
        this.albums = albums;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.count);
        dest.writeTypedList(this.albums);
    }

    public ProducerAlbumListData() {
    }

    protected ProducerAlbumListData(Parcel in) {
        this.count = in.readInt();
        this.albums = in.createTypedArrayList(ProducerAlbumData.CREATOR);
    }

    public static final Creator<ProducerAlbumListData> CREATOR = new Creator<ProducerAlbumListData>() {
        @Override
        public ProducerAlbumListData createFromParcel(Parcel source) {
            return new ProducerAlbumListData(source);
        }

        @Override
        public ProducerAlbumListData[] newArray(int size) {
            return new ProducerAlbumListData[size];
        }
    };
}
