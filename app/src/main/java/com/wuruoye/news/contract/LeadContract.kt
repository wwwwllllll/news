package com.wuruoye.news.contract

import com.wuruoye.library.contract.WIView
import com.wuruoye.library.contract.WPresenter

/**
 * @Created : wuruoye
 * @Date : 2018/7/17 10:17.
 * @Description :
 */
class LeadContract {
    interface View : WIView {
        fun goToNext()
    }

    abstract class Presenter : WPresenter<View>() {
        abstract fun setLead()
    }
}