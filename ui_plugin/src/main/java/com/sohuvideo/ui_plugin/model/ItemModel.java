package com.sohuvideo.ui_plugin.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;


public class ItemModel  implements Serializable, Parcelable {
    public static final int VIEW_TYPE_COUNT = 3;
    public static final int ITEM_TYPE_NORMAL_TITLE = 0; //标题
    public static final int ITEM_TYPE_NORMAL_VIDEO_LIST = 1; //视频
    public static final int ITEM_TYPE_VIDEO_LIST_SEPARATOR = 2; //分隔线


    private int itemType;
    private int category_code;
    private String title;
    private List<Column> mColumnList;
    private int size;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCategory_code() {
        return category_code;
    }

    public void setCategory_code(int category_code) {
        this.category_code = category_code;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Column> getmColumnList() {
        return mColumnList;
    }

    public void setmColumnList(List<Column> mColumnList) {
        this.mColumnList = mColumnList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.itemType);
        dest.writeInt(this.category_code);
        dest.writeString(this.title);
        dest.writeTypedList(this.mColumnList);
        dest.writeInt(this.size);
    }

    public ItemModel() {
    }

    protected ItemModel(Parcel in) {
        this.itemType = in.readInt();
        this.category_code = in.readInt();
        this.title = in.readString();
        this.mColumnList = in.createTypedArrayList(Column.CREATOR);
        this.size = in.readInt();
    }

    public static final Creator<ItemModel> CREATOR = new Creator<ItemModel>() {
        @Override
        public ItemModel createFromParcel(Parcel source) {
            return new ItemModel(source);
        }

        @Override
        public ItemModel[] newArray(int size) {
            return new ItemModel[size];
        }
    };
}
