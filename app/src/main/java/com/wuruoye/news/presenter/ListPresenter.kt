package com.wuruoye.news.presenter

import com.wuruoye.library.model.Listener
import com.wuruoye.library.util.net.WNet
import com.wuruoye.news.contract.ArticleListContract
import com.wuruoye.news.model.API
import com.wuruoye.news.model.UserCache
import com.wuruoye.news.model.util.DataUtil

/**
 * @Created : wuruoye
 * @Date : 2018/7/10 19:35.
 * @Description :
 */
class ListPresenter : ArticleListContract.Presenter() {
    private val mUserCache = UserCache.getInstance()

    override fun getWeb(): Boolean {
        return mUserCache.web
    }

    override fun requestList(category: String, url: String, page: String) {
        if (mUserCache.proxy) {
            val values = mapOf(Pair("app", "sina"), Pair("category", category),
                    Pair("page", page))
            WNet.getInBackground(API.ARTICLE_LIST_, values, object : Listener<String> {
                override fun onFail(p0: String?) {

                }

                override fun onSuccessful(p0: String?) {
                    val list = DataUtil.parseArticleList(p0!!)
                    val add = page != "1"
                    view?.onResultList(list, add)
                }

            })
        }else {
            val url = url.replace("\${page}", page)
            WNet.postInBackground(url, mapOf(), object : Listener<String> {
                override fun onFail(p0: String?) {

                }

                override fun onSuccessful(p0: String?) {
                    val values = mapOf(Pair("app", "sina"),
                            Pair("category", category), Pair("page", page), Pair("content", p0!!))
                    WNet.postInBackground(API.ARTICLE_LIST, values, object : Listener<String> {
                        override fun onFail(p0: String?) {

                        }

                        override fun onSuccessful(p0: String?) {
                            val list = DataUtil.parseArticleList(p0!!)
                            val add = page != "1"
                            view?.onResultList(list, add)
                        }

                    })
                }

            })
        }
    }
}