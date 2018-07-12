package com.wuruoye.news.contract

import com.wuruoye.library.contract.WIView
import com.wuruoye.library.contract.WPresenter

/**
 * @Created : wuruoye
 * @Date : 2018/7/12 15:00.
 * @Description :
 */
class VideoContract {
    interface View : WIView {

    }

    abstract class Presenter : WPresenter<View>() {

    }
}