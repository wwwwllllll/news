package com.wuruoye.news.model

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
        const val API = "api"
        const val IS_LOGIN = "is_login"
        const val USER_ID = "user_id"
        const val USER_PWD = "user_pwd"
        const val NO_IMG = "no_img"
        const val PROXY = "proxy"
        const val WEB = "web"
        const val TEXT_SIZE = "text_size"
        const val LEAD = "lead"

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

    var noImg: Boolean
        get() = getBoolean(NO_IMG, false)
        set(value) = putBoolean(NO_IMG, value)

    var proxy: Boolean
        get() = getBoolean(PROXY, false)
        set(value) = putBoolean(PROXY, value)

    var web: Boolean
        get() = getBoolean(WEB, false)
        set(value) = putBoolean(WEB, value)

    var textSize: Float
        get() = getFloat(TEXT_SIZE, 1F)
        set(value) = putFloat(TEXT_SIZE, value)

    var isLead: Boolean
        get() = getBoolean(LEAD, false)
        set(value) = putBoolean(LEAD, value)
}