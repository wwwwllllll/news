package com.wuruoye.news.contract

import com.wuruoye.library.contract.WIView
import com.wuruoye.library.contract.WPresenter

/**
 * @Created : wuruoye
 * @Date : 2018/7/13 11:17.
 * @Description :
 */
class StartContract {
    interface View : WIView {
        fun onResultCheck(login: Boolean)
    }

    abstract class Presenter : WPresenter<View>() {
        abstract fun checkLogin()
        abstract fun getTextSize(): Float
    }
}