package com.wuruoye.news.presenter

import com.wuruoye.library.model.Listener
import com.wuruoye.library.util.net.WNet
import com.wuruoye.news.contract.LoginRegisterContract
import com.wuruoye.news.model.API
import com.wuruoye.news.model.util.DataUtil
import com.wuruoye.news.model.util.SecretUtil
import java.util.regex.Pattern

/**
 * @Created : wuruoye
 * @Date : 2018/7/12 21:03.
 * @Description :
 */
class RegisterPresenter : LoginRegisterContract.Presenter() {
    override fun checkEmail(email: String): Boolean {
        val check = "\"^([a-z0-9A-Z]+[-|\\\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]" +
                "+(-[a-z0-9A-Z]+)?\\\\.)+[a-zA-Z]{2,}\$\""
        val regex = Pattern.compile(check)
        val matcher = regex.matcher(email)
        return matcher.matches()
    }

    override fun requestRegister(id: String, nickname: String, pwd: String, email: String) {
        val values = mapOf(Pair("id", id), Pair("name", nickname),
                Pair("password", SecretUtil.getPublicSecret(pwd)),
                Pair("email",   SecretUtil.getPublicSecret(email)))
        WNet.postInBackground(API.USER, values, object : Listener<String> {
            override fun onFail(p0: String?) {
                view?.onResultRegister(false, p0!!)
            }

            override fun onSuccessful(p0: String?) {
                val result = DataUtil.parseResult(p0!!)
                view?.onResultRegister(result.result, result.info)
            }

        })
    }
}