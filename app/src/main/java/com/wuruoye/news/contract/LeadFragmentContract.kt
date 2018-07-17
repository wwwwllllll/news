package com.wuruoye.news.contract

import com.wuruoye.library.contract.WIView
import com.wuruoye.library.contract.WPresenter

/**
 * @Created : wuruoye
 * @Date : 2018/7/17 10:35.
 * @Description :
 */
class LeadFragmentContract {
    interface View : WIView {

    }

    abstract class Presenter : WPresenter<View>() {

    }
}