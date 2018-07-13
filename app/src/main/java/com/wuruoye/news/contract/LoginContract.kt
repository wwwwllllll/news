package com.wuruoye.news.contract

import android.content.Intent
import com.wuruoye.library.contract.WIView
import com.wuruoye.library.contract.WPresenter

/**
 * @Created : wuruoye
 * @Date : 2018/7/12 20:05.
 * @Description :
 */
class LoginContract {
    interface View : WIView {
        fun goToRegister()
        fun goToLogin()
        fun setLoginResult(result: Int, data: Intent)
    }

    abstract class Presenter : WPresenter<View>() {

    }
}