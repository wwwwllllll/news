package com.wuruoye.news.contract

import com.wuruoye.library.contract.WIView
import com.wuruoye.library.contract.WPresenter
import com.wuruoye.news.model.bean.ArticleItem
import com.wuruoye.news.model.bean.ArticleList

/**
 * @Created : wuruoye
 * @Date : 2018/7/10 19:15.
 * @Description :
 */
class ArticleListContract {
    interface View : WIView {
        fun onResultList(articleList: ArticleList)
    }

    abstract class Presenter : WPresenter<View>() {
        abstract fun requestList(category: String, url: String, page: String)
    }
}