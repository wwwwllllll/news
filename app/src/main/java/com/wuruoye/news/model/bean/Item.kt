package com.wuruoye.news.model.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * @Created : wuruoye
 * @Date : 2018/7/10 09:45.
 * @Description : 单个栏目类
 */
data class Item(
        var title: String,
        var url: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Item> {
        override fun createFromParcel(parcel: Parcel): Item {
            return Item(parcel)
        }

        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }
    }
}