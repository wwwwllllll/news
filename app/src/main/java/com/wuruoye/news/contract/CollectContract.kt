package com.wuruoye.news.contract

import com.wuruoye.library.contract.WIView
import com.wuruoye.library.contract.WPresenter
import com.wuruoye.news.model.bean.ArticleCollect

class CollectContract {
    interface View : WIView {
        fun onResultCollectList(collectList: List<ArticleCollect>)
        fun onResultCollectList(info: String)
        fun onResultCancelCollect(result: Boolean, info: String)
    }

    abstract class Presenter: WPresenter<View>() {
        abstract fun requestCollectList(user: String)
        abstract fun requestCancelCollect(collect: Int)
    }
}