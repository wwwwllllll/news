package com.wuruoye.news.contract

import com.wuruoye.library.contract.WIView
import com.wuruoye.library.contract.WPresenter
import com.wuruoye.news.model.bean.ArticleComment
import com.wuruoye.news.model.bean.ArticleDetail

/**
 * @Created : wuruoye
 * @Date : 2018/7/11 21:45.
 * @Description :
 */
class DetailContract {
    interface View : WIView {
        fun onResultDetail(detail: ArticleDetail)
        fun onResultCommentList(commentList: List<ArticleComment>)
        fun onResultPraiseComment(result: Boolean, info: String)
        fun onResultCommentComment(comment: ArticleComment)
        fun onResultCommentComment(info: String)
    }

    abstract class Presenter : WPresenter<View>() {
        abstract fun requestDetail(app: String, category: String, id: String)
        abstract fun requestCommentList(article: String)
        abstract fun requestPraiseComment(comment: Int)
        abstract fun requestComment(article: String, content: String, parent: Int)
    }
}