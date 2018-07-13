package com.wuruoye.news.model.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * @Created : wuruoye
 * @Date : 2018/7/13 08:27.
 * @Description :
 */
data class LoginUser(
        var avatar: String,
        var create_time: Long,
        var email: String,
        var email_confirm: Boolean,
        var id: String,
        var is_available: Boolean,
        var name: String,
        val phone: String,
        var sign: String
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readLong(),
            source.readString(),
            1 == source.readInt(),
            source.readString(),
            1 == source.readInt(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(avatar)
        writeLong(create_time)
        writeString(email)
        writeInt((if (email_confirm) 1 else 0))
        writeString(id)
        writeInt((if (is_available) 1 else 0))
        writeString(name)
        writeString(phone)
        writeString(sign)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<LoginUser> = object : Parcelable.Creator<LoginUser> {
            override fun createFromParcel(source: Parcel): LoginUser = LoginUser(source)
            override fun newArray(size: Int): Array<LoginUser?> = arrayOfNulls(size)
        }
    }
}