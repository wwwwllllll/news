package com.wuruoye.news.contract

import com.wuruoye.library.contract.WIView
import com.wuruoye.library.contract.WPresenter

/**
 * @Created : wuruoye
 * @Date : 2018/7/11 21:45.
 * @Description :
 */
class DetailContract {
    interface View : WIView {

    }

    abstract class Presenter : WPresenter<View>() {

    }
}