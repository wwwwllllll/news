package com.wuruoye.news.model

import android.support.v4.util.ArrayMap
import com.google.gson.Gson
import com.wuruoye.library.model.WBaseCache
import com.wuruoye.news.model.bean.Api

/**
 * @Created : wuruoye
 * @Date : 2018/7/10 10:06.
 * @Description : 用户数据缓存
 */
class UserCache private constructor(): WBaseCache("user") {
    companion object {
        private val sInstance = UserCache()
        val gson = Gson()
        const val API = "api"

        fun getInstance(): UserCache{
            return sInstance
        }
    }

    lateinit var api: Map<String, Api>
}