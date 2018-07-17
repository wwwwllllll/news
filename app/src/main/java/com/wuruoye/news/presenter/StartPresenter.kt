package com.wuruoye.news.presenter

import com.wuruoye.library.model.Listener
import com.wuruoye.library.util.net.WNet
import com.wuruoye.news.contract.StartContract
import com.wuruoye.news.model.API
import com.wuruoye.news.model.UserCache
import com.wuruoye.news.model.util.DataUtil

/**
 * @Created : wuruoye
 * @Date : 2018/7/13 11:19.
 * @Description :
 */
class StartPresenter : StartContract.Presenter() {
    private val mUserCache = UserCache.getInstance()

    override fun getTextSize(): Float {
        return mUserCache.textSize
    }

    override fun isLead(): Boolean {
        return mUserCache.isLead
    }

    override fun checkLogin() {
        if (!mUserCache.isLogin) {
            view?.onResultCheck(false)
        }else {
            val values = mapOf(Pair("id", mUserCache.userId),
                    Pair("password", mUserCache.userPwd))
            WNet.getInBackground(API.USER_LOGIN, values, object : Listener<String> {
                override fun onFail(p0: String?) {
                    view?.onResultCheck(false)
                }

                override fun onSuccessful(p0: String?) {
                    val result = DataUtil.parseResult(p0!!)
                    view?.onResultCheck(result.result)
                }

            })
        }
    }
}