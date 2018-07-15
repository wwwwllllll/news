package com.wuruoye.news.contract

import com.wuruoye.library.contract.WIView
import com.wuruoye.library.contract.WPresenter
import com.wuruoye.news.model.bean.LoginUser

/**
 * @Created : wuruoye
 * @Date : 2018/7/9 16:54.
 * @Description :
 */
class UserContract {
    interface View : WIView {
        fun onLogin()
        fun onResultUserInfo(loginUser: LoginUser)
        fun onResultUserInfo(info: String)
    }

    abstract class Presenter : WPresenter<View>() {
        abstract fun isLogin(): Boolean
        abstract fun requestUserInfo()
    }
}