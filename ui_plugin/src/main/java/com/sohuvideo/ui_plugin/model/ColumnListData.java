package com.sohuvideo.ui_plugin.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class ColumnListData  implements Serializable, Parcelable {

    private String column_name;
    private int category_code;
    private List<Column> videos;

    public String getColumn_name() {
        return column_name;
    }

    public void setColumn_name(String column_name) {
        this.column_name = column_name;
    }

    public int getCategory_code() {
        return category_code;
    }

    public void setCategory_code(int category_code) {
        this.category_code = category_code;
    }

    public List<Column> getVideos() {
        return videos;
    }

    public void setVideos(List<Column> videos) {
        this.videos = videos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.column_name);
        dest.writeInt(this.category_code);
        dest.writeTypedList(this.videos);
    }

    public ColumnListData() {
    }

    protected ColumnListData(Parcel in) {
        this.column_name = in.readString();
        this.category_code = in.readInt();
        this.videos = in.createTypedArrayList(Column.CREATOR);
    }

    public static final Creator<ColumnListData> CREATOR = new Creator<ColumnListData>() {
        @Override
        public ColumnListData createFromParcel(Parcel source) {
            return new ColumnListData(source);
        }

        @Override
        public ColumnListData[] newArray(int size) {
            return new ColumnListData[size];
        }
    };
}
