package com.wuruoye.news.contract

import com.wuruoye.library.contract.WIView
import com.wuruoye.library.contract.WPresenter
import com.wuruoye.news.model.bean.ArticleDetail

/**
 * @Created : wuruoye
 * @Date : 2018/7/11 21:45.
 * @Description :
 */
class DetailContract {
    interface View : WIView {
        fun onResultDetail(detail: ArticleDetail)
    }

    abstract class Presenter : WPresenter<View>() {
        abstract fun requestDetail(app: String, category: String, id: String)
    }
}