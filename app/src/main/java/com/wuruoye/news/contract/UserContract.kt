package com.wuruoye.news.contract

import com.wuruoye.library.contract.WIView
import com.wuruoye.library.contract.WPresenter

/**
 * @Created : wuruoye
 * @Date : 2018/7/9 16:54.
 * @Description :
 */
class UserContract {
    interface View : WIView {

    }

    abstract class Presenter : WPresenter<View>() {

    }
}