package com.wuruoye.news.contract

import com.wuruoye.library.contract.WIView
import com.wuruoye.library.contract.WPresenter

/**
 * @Created : wuruoye
 * @Date : 2018/7/12 20:08.
 * @Description :
 */
class LoginRegisterContract {
    interface View : WIView {
        fun onResultRegister(ok: Boolean, info: String)
    }

    abstract class Presenter : WPresenter<View>() {
        abstract fun checkEmail(email: String): Boolean
        abstract fun requestRegister(id: String, nickname: String, pwd: String, email: String)
    }
}