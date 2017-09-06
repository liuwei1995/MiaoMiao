/**
 *
 */
package com.sohuvideo.ui_plugin.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.sohuvideo.ui_plugin.control.V4APIResponseCommon;

import java.io.Serializable;

/**
 * @author kimwang
 */
public class UidData extends V4APIResponseCommon  implements Serializable, Parcelable {

    private Uid data;

    public Uid getData() {
        return data;
    }

    public void setData(Uid data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.data, flags);
    }

    public UidData() {
    }

    protected UidData(Parcel in) {
        this.data = in.readParcelable(Uid.class.getClassLoader());
    }

    public static final Creator<UidData> CREATOR = new Creator<UidData>() {
        @Override
        public UidData createFromParcel(Parcel source) {
            return new UidData(source);
        }

        @Override
        public UidData[] newArray(int size) {
            return new UidData[size];
        }
    };
}
