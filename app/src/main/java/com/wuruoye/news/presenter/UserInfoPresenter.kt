package com.wuruoye.news.presenter

import com.wuruoye.library.model.Listener
import com.wuruoye.library.model.WConfig
import com.wuruoye.library.util.FileUtil
import com.wuruoye.library.util.net.IWNet
import com.wuruoye.library.util.net.WNet
import com.wuruoye.news.contract.UserInfoContract
import com.wuruoye.news.model.API
import com.wuruoye.news.model.bean.LoginUser
import com.wuruoye.news.model.util.DataUtil
import java.util.regex.Pattern

/**
 * @Created : wuruoye
 * @Date : 2018/7/14 14:51.
 * @Description :
 */
class UserInfoPresenter : UserInfoContract.Presenter() {

    override fun getAvatarPath(): String {
        val path = WConfig.IMAGE_PATH + "avatar.jpg"
        FileUtil.checkFile(path)
        return path
    }

    override fun getAvatarPath(avatar: String): String {
        return "https://all.wuruoye.com/user/avatar/$avatar"
    }

    override fun checkEmail(email: String): Boolean {
        val check = "\"^([a-z0-9A-Z]+[-|\\\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]" +
                "+(-[a-z0-9A-Z]+)?\\\\.)+[a-zA-Z]{2,}\$\""
        val regex = Pattern.compile(check)
        val matcher = regex.matcher(email)
        return matcher.matches()
    }

    override fun checkPhone(phone: String): Boolean {
        return phone.length == 11
    }

    override fun requestUpload(path: String) {
        WNet.uploadFileInBackground(API.USER_AVATAR, "avatar", path, "image/*",
                object : Listener<String> {
                    override fun onFail(p0: String?) {
                        view?.onResultAvatar(false, p0!!)
                    }

                    override fun onSuccessful(p0: String?) {
                        val result = DataUtil.parseResult(p0!!)
                        view?.onResultAvatar(result.result, result.info)
                    }
        })
    }

    override fun requestUserInfo(userInfo: LoginUser) {
        val values = mapOf(Pair("avatar", userInfo.avatar),
                Pair("name", userInfo.name), Pair("sign", userInfo.sign),
                Pair("email", userInfo.email), Pair("phone", userInfo.phone))

        WNet.requestInBackground(API.USER, values, object : Listener<String> {
            override fun onFail(p0: String?) {
                view?.onResultUserInfo(p0!!)
            }

            override fun onSuccessful(p0: String?) {
                val result = DataUtil.parseResult(p0!!)
                if (result.result) {
                    view?.onResultUserInfo(DataUtil.parseLoginUser(result.info))
                }else {
                    view?.onResultUserInfo(result.info)
                }
            }
        }, IWNet.METHOD.PUT)
    }
}