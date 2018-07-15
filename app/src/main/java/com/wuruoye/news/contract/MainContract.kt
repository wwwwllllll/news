package com.wuruoye.news.contract

import com.wuruoye.library.contract.WIView
import com.wuruoye.library.contract.WPresenter

/**
 * @Created : wuruoye
 * @Date : 2018/7/9 12:03.
 * @Description :
 */
class MainContract {
    interface View : WIView {
        fun onResultApi()
        fun onLogin()
    }

    abstract class Presenter : WPresenter<View>() {
        abstract fun requestApi()
    }
}