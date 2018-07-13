package com.wuruoye.news.presenter

import com.wuruoye.library.model.Listener
import com.wuruoye.library.util.net.WNet
import com.wuruoye.news.contract.MainContract
import com.wuruoye.news.model.API
import com.wuruoye.news.model.UserCache
import com.wuruoye.news.model.util.DataUtil

/**
 * @Created : wuruoye
 * @Date : 2018/7/10 10:20.
 * @Description :
 */
class MainPresenter : MainContract.Presenter() {
    private val userCache = UserCache.getInstance()

    override fun requestApi() {
        val api = userCache.cachedApi
        if (api.isEmpty()) {
            WNet.getInBackground(API.API, mapOf(), object : Listener<String> {
                override fun onFail(p0: String?) {

                }

                override fun onSuccessful(p0: String?) {
                    val api = DataUtil.parseApi(p0!!)
                    userCache.api = api
                    userCache.cachedApi = p0
                    view?.onResultApi()
                }

            })
        }else {
            userCache.api = DataUtil.parseApi(api)
            view?.onResultApi()
        }
    }
}