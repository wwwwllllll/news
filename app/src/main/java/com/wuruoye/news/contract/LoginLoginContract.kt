package com.wuruoye.news.contract

import com.wuruoye.library.contract.WIView
import com.wuruoye.library.contract.WPresenter
import com.wuruoye.news.model.bean.LoginUser

/**
 * @Created : wuruoye
 * @Date : 2018/7/12 20:08.
 * @Description :
 */
class LoginLoginContract {
    interface View : WIView {
        fun onResultLoginInfo(info: String)
        fun onResultLogin(loginUser: LoginUser)
    }

    abstract class Presenter : WPresenter<View>() {
        abstract fun requestLogin(id: String, pwd: String)
    }
}