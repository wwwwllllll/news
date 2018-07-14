package com.wuruoye.news.presenter

import com.wuruoye.library.model.Listener
import com.wuruoye.library.util.net.IWNet
import com.wuruoye.library.util.net.WNet
import com.wuruoye.news.contract.CollectContract
import com.wuruoye.news.model.API
import com.wuruoye.news.model.util.DataUtil

/**
 * @Created : wuruoye
 * @Date : 2018/7/14 20:44.
 * @Description :
 */
class CollectPresenter : CollectContract.Presenter() {
    private var mNext = 0L

    override fun requestCollectList(user: String) {
        val values = mapOf(Pair("user", user),
                Pair("time", mNext.toString()))

        WNet.getInBackground(API.ARTICLE_COLLECT, values, object : Listener<String> {
            override fun onFail(p0: String?) {
                view?.onResultCollectList(p0!!)
            }

            override fun onSuccessful(p0: String?) {
                val result = DataUtil.parseResult(p0!!)
                if (result.result) {
                    val list = DataUtil.parseArticleCollectList(result.info)
                    mNext = list.next
                    view?.onResultCollectList(list.list)
                }else {
                    view?.onResultCollectList(result.info)
                }
            }
        })
    }

    override fun requestCancelCollect(collect: Int) {
        val values = mapOf(Pair("id", collect.toString()))

        WNet.requestInBackground(API.ARTICLE_COLLECT, values, object : Listener<String> {
            override fun onFail(p0: String?) {
                view?.onResultCancelCollect(false, p0!!)
            }

            override fun onSuccessful(p0: String?) {
                val result = DataUtil.parseResult(p0!!)
                view?.onResultCancelCollect(result.result, result.info)
            }
        }, IWNet.METHOD.DELETE)
    }
}