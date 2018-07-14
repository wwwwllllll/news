package com.wuruoye.news.presenter

import com.wuruoye.library.model.Listener
import com.wuruoye.library.util.net.WNet
import com.wuruoye.news.contract.LoginLoginContract
import com.wuruoye.news.model.API
import com.wuruoye.news.model.UserCache
import com.wuruoye.news.model.util.DataUtil
import com.wuruoye.news.model.util.SecretUtil
import java.net.URLEncoder

/**
 * @Created : wuruoye
 * @Date : 2018/7/12 20:29.
 * @Description :
 */
class LoginPresenter : LoginLoginContract.Presenter() {
    private val mUserCache = UserCache.getInstance()

    override fun requestLogin(id: String, pwd: String) {
        val sPwd = URLEncoder.encode(SecretUtil.getPublicSecret(pwd), "utf8")
        val values = mapOf(Pair("id", id),
                Pair("password", sPwd))
        WNet.getInBackground(API.USER_LOGIN, values, object : Listener<String> {
            override fun onFail(p0: String?) {
                view?.onResultLoginInfo(p0!!)
            }

            override fun onSuccessful(p0: String?) {
                val result = DataUtil.parseResult(p0!!)
                if (result.result) {
                    val user = DataUtil.parseLoginUser(result.info)
                    mUserCache.isLogin = true
                    mUserCache.userId = id
                    mUserCache.userPwd = sPwd
                    view?.onResultLogin(user)
                }else {
                    view?.onResultLoginInfo(result.info)
                }
            }

        })
    }
}