package com.wuruoye.news.model.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * @Created : wuruoye
 * @Date : 2018/7/12 08:23.
 * @Description :
 */
data class DetailItem(
        var type: Int,
        var info: String
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(type)
        writeString(info)
    }

    companion object {
        const val TYPE_TEXT = 1

        const val TYPE_IMG = 2

        const val TYPE_VIDEO = 3

        const val TYPE_H1 = 4

        const val TYPE_H2 = 5

        const val TYPE_LI = 6

        const val TYPE_TEXT_CEN = 7

        const val TYPE_QUOTE = 8

        const val TYTPE_H3 = 9

        @JvmField
        val CREATOR: Parcelable.Creator<DetailItem> = object : Parcelable.Creator<DetailItem> {
            override fun createFromParcel(source: Parcel): DetailItem = DetailItem(source)
            override fun newArray(size: Int): Array<DetailItem?> = arrayOfNulls(size)
        }
    }
}