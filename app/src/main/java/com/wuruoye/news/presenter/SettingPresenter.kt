package com.wuruoye.news.presenter

import com.wuruoye.library.model.Listener
import com.wuruoye.library.util.net.WNet
import com.wuruoye.news.contract.SettingContract
import com.wuruoye.news.model.API
import com.wuruoye.news.model.UserCache
import com.wuruoye.news.model.util.DataUtil

/**
 * @Created : wuruoye
 * @Date : 2018/7/15 15:07.
 * @Description :
 */
class SettingPresenter : SettingContract.Presenter() {
    private val mUserCache = UserCache.getInstance()

    override fun isLogin(): Boolean {
        return mUserCache.isLogin
    }

    override fun getNoImg(): Boolean {
        return mUserCache.noImg
    }

    override fun getProxy(): Boolean {
        return mUserCache.proxy
    }

    override fun getWeb(): Boolean {
        return mUserCache.web
    }

    override fun getTextSize(): Float {
        return mUserCache.textSize
    }

    override fun setNoImg(check: Boolean) {
        mUserCache.noImg = check
    }

    override fun setProxy(check: Boolean) {
        mUserCache.proxy = check
    }

    override fun setWeb(check: Boolean) {
        mUserCache.web = check
    }

    override fun setTextSize(scale: Float) {
        mUserCache.textSize = scale
    }

    override fun requestLogout() {
        WNet.getInBackground(API.USER_LOGOUT, mapOf(), object : Listener<String> {
            override fun onFail(p0: String?) {
                view?.onResultLogout(false, p0!!)
            }

            override fun onSuccessful(p0: String?) {
                val result = DataUtil.parseResult(p0!!)
                mUserCache.isLogin = false
                view?.onResultLogout(result.result, result.info)
            }
        })
    }

    override fun requestReloadApi() {
        WNet.getInBackground(API.API, mapOf(), object : Listener<String> {
            override fun onFail(p0: String?) {
                view?.onResultReloadApi(false, p0!!)
            }

            override fun onSuccessful(p0: String?) {
                val api = DataUtil.parseApi(p0!!)
                mUserCache.api = api
                mUserCache.cachedApi = p0
                view?.onResultReloadApi(true, "加载成功")
            }
        })
    }
}