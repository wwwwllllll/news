package com.wuruoye.news.model.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * @Created : wuruoye
 * @Date : 2018/7/10 19:10.
 * @Description :
 */
data class ArticleItem(
        var id: String,
        var title: String,
        var forward: String,
        var author: String,
        var millis: Long,
        var bg_img: String,
        var url: String,
        var type: String,
        var open: String
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readLong(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(title)
        writeString(forward)
        writeString(author)
        writeLong(millis)
        writeString(bg_img)
        writeString(url)
        writeString(type)
        writeString(open)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ArticleItem> = object : Parcelable.Creator<ArticleItem> {
            override fun createFromParcel(source: Parcel): ArticleItem = ArticleItem(source)
            override fun newArray(size: Int): Array<ArticleItem?> = arrayOfNulls(size)
        }
    }
}