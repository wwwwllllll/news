package com.wuruoye.news.ui

import android.os.Bundle
import android.text.format.DateUtils
import com.wuruoye.library.ui.WBaseActivity
import com.wuruoye.library.util.DateUtil
import com.wuruoye.news.R
import com.wuruoye.news.contract.UserInfoContact
import com.wuruoye.news.model.bean.LoginUser
import com.wuruoye.news.model.util.DataUtil
import com.wuruoye.news.presenter.UserInfoPresenter
import kotlinx.android.synthetic.main.activity_userinfo.*

class UserInfoActivity: WBaseActivity<UserInfoContact.Presenter>(), UserInfoContact.View{

    override fun GetUserInfo(user: LoginUser) {
        setData(user)
    }

    private var muserid:String? = null

    override fun getContentView(): Int {
        return R.layout.activity_userinfo
    }

    override fun initData(p0: Bundle?) {
        muserid = p0!!.getString("id")
    }

    override fun initView() {
        userinfo_back_bt.setOnClickListener {
            onBackPressed()
        }
    }

    fun setData(user: LoginUser) {
        info_user_name.text = user.name
        info_user_id.text = user.id
        info_user_email.text = user.email
        info_user_phone.text = user.phone
        info_user_creattime.text = DateUtil.formatTime(user.create_time*1000,"YYYY-MM-dd HH:MM")
    }
}