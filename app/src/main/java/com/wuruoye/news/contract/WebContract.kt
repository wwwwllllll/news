package com.wuruoye.news.contract

import com.wuruoye.library.contract.WIView
import com.wuruoye.library.contract.WPresenter

/**
 * @Created : wuruoye
 * @Date : 2018/7/12 09:19.
 * @Description :
 */
class WebContract {
    interface View : WIView {

    }

    abstract class Presenter : WPresenter<View>() {

    }
}