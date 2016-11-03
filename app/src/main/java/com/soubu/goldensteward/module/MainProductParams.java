package com.soubu.goldensteward.module;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lakers on 16/11/2.
 */

public class MainProductParams implements Parcelable {
    int id;
    int[] sys_tags;
    String[] custom_tags;

    public MainProductParams() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int[] getSys_tags() {
        return sys_tags;
    }

    public void setSys_tags(int[] sys_tags) {
        this.sys_tags = sys_tags;
    }

    public String[] getCustom_tags() {
        return custom_tags;
    }

    public void setCustom_tags(String[] custom_tags) {
        this.custom_tags = custom_tags;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        if(sys_tags != null){
            dest.writeIntArray(sys_tags);
        }
        if(custom_tags != null){
            dest.writeStringArray(custom_tags);
        }
    }

    public static final Parcelable.Creator<MainProductParams> CREATOR = new Parcelable.Creator<MainProductParams>() {
        public MainProductParams createFromParcel(Parcel in) {
            return new MainProductParams(in);
        }

        public MainProductParams[] newArray(int size) {
            return new MainProductParams[size];
        }
    };

    private MainProductParams(Parcel in) {
        id = in.readInt();
        sys_tags = in.createIntArray();
        custom_tags = in.createStringArray();
//        //开始读数组的长度
//        int length = in.readInt() ;
//        Log.e("sys_tags", " length : "+length);
////        int[] sys_tags = null ;
//        //如果数组长度大于0，那么就读数组， 所有数组的操作都可以这样。
//        if(length>0){
//            sys_tags = new int[length];
//            in.readIntArray(sys_tags);
//        }
//        length = in.readInt();
//        Log.e("sys_tags", " length : "+length);
////        String[] custom_tags = null ;
//        if(length>0){
//            custom_tags = new String[length];
//            in.readStringArray(custom_tags);
//        }
    }
}
