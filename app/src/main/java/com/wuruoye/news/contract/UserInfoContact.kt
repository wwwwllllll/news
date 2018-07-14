package com.wuruoye.news.contract

import com.wuruoye.library.contract.WIView
import com.wuruoye.library.contract.WPresenter
import com.wuruoye.news.model.bean.LoginUser

class UserInfoContact {
    interface View:WIView {
        fun UserInfo(user:LoginUser)
    }

    abstract class Presenter:WPresenter<View>() {
        abstract fun GetUserInfo(userid:String)
    }
}