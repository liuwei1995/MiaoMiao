/**
 *
 */
package com.sohuvideo.ui_plugin.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * @author kimwang
 */
public class Uid  implements Serializable, Parcelable {

    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uid);
    }

    public Uid() {
    }

    protected Uid(Parcel in) {
        this.uid = in.readString();
    }

    public static final Creator<Uid> CREATOR = new Creator<Uid>() {
        @Override
        public Uid createFromParcel(Parcel source) {
            return new Uid(source);
        }

        @Override
        public Uid[] newArray(int size) {
            return new Uid[size];
        }
    };
}
