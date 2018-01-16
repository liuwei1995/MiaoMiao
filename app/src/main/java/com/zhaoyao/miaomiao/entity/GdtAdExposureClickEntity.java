package com.zhaoyao.miaomiao.entity;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import com.tgb.lk.ahibernate.annotation.Column;
import com.tgb.lk.ahibernate.annotation.Id;
import com.tgb.lk.ahibernate.annotation.Table;

/**
 * Created by liuwei on 2018/1/12 17:46
 */
@Table(name = "GdtAdExposureClickEntity")
public class GdtAdExposureClickEntity implements Parcelable {

    @Id
    @Column(name = "Id")
    private int Id;//账号id

    @Column(name = "exposureNumber")
    private Integer exposureNumber = 0;//曝光 数

    @Column(name = "clickNumber")
    private Integer clickNumber = 0;//点击数量

    @Column(name = "createTime")
    private String createTime;


    @Column(name = "androidVersion")
    private  int androidSDK  = Build.VERSION.SDK_INT;

    @Column(name = "androidVersion")
    private   String androidVersion = Build.VERSION.RELEASE;//作者

    @Column(name = "buildModel")
    private  String buildModel = Build.MODEL;//获取设备型号

    @Column(name = "buildManufacturer")
    private  String buildManufacturer =  Build.MANUFACTURER;//设备厂商  如Xiaomi

    @Column(name = "androidID")
    private  String androidID =  "";//获取设备AndroidID

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Integer getExposureNumber() {
        return exposureNumber;
    }

    public void setExposureNumber(Integer exposureNumber) {
        this.exposureNumber = exposureNumber;
    }

    public Integer getClickNumber() {
        return clickNumber;
    }

    public void setClickNumber(Integer clickNumber) {
        this.clickNumber = clickNumber;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getAndroidSDK() {
        return androidSDK;
    }

    public void setAndroidSDK(int androidSDK) {
        this.androidSDK = androidSDK;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public String getBuildModel() {
        return buildModel;
    }

    public void setBuildModel(String buildModel) {
        this.buildModel = buildModel;
    }

    public String getBuildManufacturer() {
        return buildManufacturer;
    }

    public void setBuildManufacturer(String buildManufacturer) {
        this.buildManufacturer = buildManufacturer;
    }

    public String getAndroidID() {
        return androidID;
    }

    public void setAndroidID(String androidID) {
        this.androidID = androidID;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.Id);
        dest.writeValue(this.exposureNumber);
        dest.writeValue(this.clickNumber);
        dest.writeString(this.createTime);
        dest.writeInt(this.androidSDK);
        dest.writeString(this.androidVersion);
        dest.writeString(this.buildModel);
        dest.writeString(this.buildManufacturer);
        dest.writeString(this.androidID);
    }

    public GdtAdExposureClickEntity() {
    }

    protected GdtAdExposureClickEntity(Parcel in) {
        this.Id = in.readInt();
        this.exposureNumber = (Integer) in.readValue(Integer.class.getClassLoader());
        this.clickNumber = (Integer) in.readValue(Integer.class.getClassLoader());
        this.createTime = in.readString();
        this.androidSDK = in.readInt();
        this.androidVersion = in.readString();
        this.buildModel = in.readString();
        this.buildManufacturer = in.readString();
        this.androidID = in.readString();
    }

    public static final Creator<GdtAdExposureClickEntity> CREATOR = new Creator<GdtAdExposureClickEntity>() {
        @Override
        public GdtAdExposureClickEntity createFromParcel(Parcel source) {
            return new GdtAdExposureClickEntity(source);
        }

        @Override
        public GdtAdExposureClickEntity[] newArray(int size) {
            return new GdtAdExposureClickEntity[size];
        }
    };

    @Override
    public String toString() {
        return "GdtAdExposureClickEntity{" +
                "Id=" + Id +
                ", exposureNumber=" + exposureNumber +
                ", clickNumber=" + clickNumber +
                ", createTime='" + createTime + '\'' +
                ", androidSDK=" + androidSDK +
                ", androidVersion='" + androidVersion + '\'' +
                ", buildModel='" + buildModel + '\'' +
                ", buildManufacturer='" + buildManufacturer + '\'' +
                ", androidID='" + androidID + '\'' +
                '}';
    }
}
