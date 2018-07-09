package com.wuruoye.news.contract

import com.wuruoye.library.contract.WIView
import com.wuruoye.library.contract.WPresenter

/**
 * @Created : wuruoye
 * @Date : 2018/7/9 16:53.
 * @Description :
 */
class HomeContract {
    interface View : WIView {

    }

    abstract class Presenter : WPresenter<View>() {

    }
}