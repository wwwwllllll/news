package com.wuruoye.news.presenter

import com.wuruoye.library.model.Listener
import com.wuruoye.library.util.net.WNet
import com.wuruoye.news.contract.UserInfoContact
import com.wuruoye.news.model.bean.LoginUser
import com.wuruoye.news.model.util.DataUtil

class UserInfoPresenter: UserInfoContact.Presenter(){
    override fun GetUserInfo(userid: String) {
        var value = mapOf(Pair("userid",userid))

        WNet.getInBackground("",value,object: Listener<String> {

            override fun onSuccessful(p0: String?) {
                val info = DataUtil.parseLoginUser(p0!!)
                view.UserInfo(info)
            }

            override fun onFail(p0: String?) {

            }
        })
    }
}