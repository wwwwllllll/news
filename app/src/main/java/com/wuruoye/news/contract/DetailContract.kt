package com.wuruoye.news.contract

import com.wuruoye.library.contract.WIView
import com.wuruoye.library.contract.WPresenter
import com.wuruoye.news.model.bean.ArticleComment
import com.wuruoye.news.model.bean.ArticleDetail
import com.wuruoye.news.model.bean.ArticleInfo
import com.wuruoye.news.model.bean.ArticleItem

/**
 * @Created : wuruoye
 * @Date : 2018/7/11 21:45.
 * @Description :
 */
class DetailContract {
    interface View : WIView {
        fun onResultDetail(detail: ArticleDetail)
        fun onResultCommentList(commentList: List<ArticleComment>)
        fun onResultCommentList(info: String)
        fun onResultPraiseComment(result: Boolean, info: String)
        fun onResultComment(comment: ArticleComment)
        fun onResultComment(info: String)
        fun onResultArticleInfo(info: ArticleInfo)
        fun onResultArticleInfo(info: String)
        fun onResultPraiseArticle(result: Boolean, info: String)
        fun onResultCollectArticle(result: Boolean, info: String)
    }

    abstract class Presenter : WPresenter<View>() {
        abstract fun isLogin(): Boolean
        abstract fun isNoImg(): Boolean
        abstract fun requestDetail(app: String, category: String, id: String)
        abstract fun requestCommentList(article: String)
        abstract fun requestPraiseComment(comment: Int)
        abstract fun requestComment(article: String, content: String, parent: Int)
        abstract fun requestArticleInfo(article: String)
        abstract fun requestPraiseArticle(article: String)
        abstract fun requestCollectArticle(article: String, content: ArticleItem)
    }
}