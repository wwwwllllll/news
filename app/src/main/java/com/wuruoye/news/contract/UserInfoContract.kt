package com.wuruoye.news.contract

import com.wuruoye.library.contract.WIView
import com.wuruoye.library.contract.WPresenter
import com.wuruoye.news.model.bean.LoginUser

class UserInfoContract {
    interface View : WIView {
        fun onResultAvatar(result: Boolean, info: String)
        fun onResultUserInfo(info: LoginUser)
        fun onResultUserInfo(info: String)
    }

    abstract class Presenter : WPresenter<View>() {
        abstract fun getAvatarPath(): String
        abstract fun getAvatarPath(avatar: String): String
        abstract fun checkEmail(email: String): Boolean
        abstract fun checkPhone(phone: String): Boolean
        abstract fun requestUpload(path: String)
        abstract fun requestUserInfo(userInfo: LoginUser)
    }
}