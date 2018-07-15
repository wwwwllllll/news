package com.wuruoye.news.presenter

import com.google.gson.Gson
import com.wuruoye.library.model.Listener
import com.wuruoye.library.util.net.WNet
import com.wuruoye.library.util.net.WNet.getInBackground
import com.wuruoye.news.contract.DetailContract
import com.wuruoye.news.model.API
import com.wuruoye.news.model.UserCache
import com.wuruoye.news.model.bean.ArticleItem
import com.wuruoye.news.model.util.DataUtil

/**
 * @Created : wuruoye
 * @Date : 2018/7/12 11:04.
 * @Description :
 */
class DetailPresenter : DetailContract.Presenter() {
    private val mUserCache = UserCache.getInstance()
    private var mNextComment = 0L

    override fun isLogin(): Boolean {
        return mUserCache.isLogin
    }

    override fun isNoImg(): Boolean {
        return mUserCache.noImg
    }

    override fun requestDetail(app: String, category: String, id: String) {
        val values = mapOf(Pair("app", app), Pair("category", category),
                Pair("id", id))
        getInBackground(API.ARTICLE_DETAIL, values, object : Listener<String> {
            override fun onFail(p0: String?) {

            }

            override fun onSuccessful(p0: String?) {
                val detail = DataUtil.parseArticleDetail(p0!!)
                view?.onResultDetail(detail)
            }

        })
    }

    override fun requestComment(article: String, content: String, parent: Int) {
        val values = mapOf(Pair("article", article),
                Pair("content", content), Pair("parent", parent.toString()))
        WNet.postInBackground(API.ARTICLE_COMMENT, values, object : Listener<String> {
            override fun onFail(p0: String?) {
                view?.onResultComment(p0!!)
            }

            override fun onSuccessful(p0: String?) {
                val result = DataUtil.parseResult(p0!!)
                if (result.result) {
                    val comment = DataUtil.parseArticleComment(result.info)
                    view?.onResultComment(comment)
                }else {
                    view?.onResultComment(result.info)
                }
            }

        })
    }

    override fun requestCommentList(article: String) {
        val values = mapOf(Pair("article", article),
                Pair("time", mNextComment.toString()))
        WNet.getInBackground(API.ARTICLE_COMMENT, values, object : Listener<String> {
            override fun onFail(p0: String?) {
                view?.onResultCommentList(p0!!)
            }

            override fun onSuccessful(p0: String?) {
                val result = DataUtil.parseResult(p0!!)
                if (result.result) {
                    val list = DataUtil.parseArticleCommentList(result.info)
                    mNextComment = list.next
                    view?.onResultCommentList(list.list)
                }else {
                    view?.onResultCommentList(result.info)
                }
            }

        })
    }

    override fun requestPraiseComment(comment: Int) {
        val values = mapOf(Pair("comment", comment.toString()))

        WNet.postInBackground(API.COMMENT_PRAISE, values, object : Listener<String> {
            override fun onFail(p0: String?) {
                view?.onResultPraiseComment(false, p0!!)
            }

            override fun onSuccessful(p0: String?) {
                val result = DataUtil.parseResult(p0!!)
                view?.onResultPraiseComment(result.result, result.info)
            }

        })
    }

    override fun requestArticleInfo(article: String) {
        val values = mapOf(Pair("article", article))

        WNet.getInBackground(API.ARTICLE_INFO, values, object : Listener<String> {
            override fun onFail(p0: String?) {
                view?.onResultArticleInfo(p0!!)
            }

            override fun onSuccessful(p0: String?) {
                val result = DataUtil.parseResult(p0!!)
                if (result.result) {
                    view?.onResultArticleInfo(DataUtil.parseArticleInfo(result.info))
                }else {
                    view?.onResultArticleInfo(result.info)
                }
            }

        })
    }

    override fun requestCollectArticle(article: String, content: ArticleItem) {
        val values = mapOf(Pair("article", article),
                Pair("content", Gson().toJson(content)))

        WNet.postInBackground(API.ARTICLE_COLLECT, values, object : Listener<String> {
            override fun onFail(p0: String?) {
                view?.onResultCollectArticle(false, p0!!)
            }

            override fun onSuccessful(p0: String?) {
                val result = DataUtil.parseResult(p0!!)
                view?.onResultCollectArticle(result.result, result.info)
            }

        })
    }

    override fun requestPraiseArticle(article: String) {
        val values = mapOf(Pair("article", article))

        WNet.postInBackground(API.ARTICLE_PRAISE, values, object : Listener<String> {
            override fun onFail(p0: String?) {
                view?.onResultPraiseArticle(false, p0!!)
            }

            override fun onSuccessful(p0: String?) {
                val result = DataUtil.parseResult(p0!!)
                view?.onResultPraiseArticle(result.result, result.info)
            }
        })
    }
}