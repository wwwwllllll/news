package com.wuruoye.news.contract

import com.wuruoye.library.contract.WIView
import com.wuruoye.library.contract.WPresenter

/**
 * @Created : wuruoye
 * @Date : 2018/7/12 08:58.
 * @Description :
 */
class ImgContract {
    interface View : WIView {

    }

    abstract class Presenter : WPresenter<View>() {

    }
}