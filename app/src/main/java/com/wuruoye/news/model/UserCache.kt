package com.wuruoye.news.model

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
        val sGson = Gson()
        const val API = "api"
        const val IS_LOGIN = "is_login"
        const val USER_ID = "user_id"
        const val USER_PWD = "user_pwd"

        fun getInstance(): UserCache{
            return sInstance
        }
    }

    lateinit var api: Map<String, Api>

    var cachedApi: String
        get() = getString(API, "")
        set(value) = putString(API, value)

    var isLogin: Boolean
        get() = getBoolean(IS_LOGIN, false)
        set(value) = putBoolean(IS_LOGIN, value)

    var userId: String
        get() = getString(USER_ID, "")
        set(value) = putString(USER_ID, value)

    var userPwd: String
        get() = getString(USER_PWD, "")
        set(value) = putString(USER_PWD, value)
}