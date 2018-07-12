package com.wuruoye.news.presenter

import com.wuruoye.library.model.Listener
import com.wuruoye.library.util.net.WNet.getInBackground
import com.wuruoye.news.contract.DetailContract
import com.wuruoye.news.model.API
import com.wuruoye.news.model.util.DataUtil

/**
 * @Created : wuruoye
 * @Date : 2018/7/12 11:04.
 * @Description :
 */
class DetailPresenter : DetailContract.Presenter() {

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
}