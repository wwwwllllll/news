package com.wuruoye.news.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import com.wuruoye.library.ui.WBaseActivity
import com.wuruoye.news.R
import com.wuruoye.news.contract.SettingContract
import com.wuruoye.news.model.Config.SETTING_LOGIN
import com.wuruoye.news.model.bean.LoginUser
import com.wuruoye.news.model.util.toast
import com.wuruoye.news.presenter.SettingPresenter
import kotlinx.android.synthetic.main.activity_setting.*

/**
 * @Created : wuruoye
 * @Date : 2018/7/15 12:46.
 * @Description :
 */
class SettingActivity : WBaseActivity<SettingContract.Presenter>(), SettingContract.View,
        View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    companion object {
        const val RESULT_LOGIN = 1
        const val RESULT_LOGOUT = 2
    }
    private var mIsLogin = false
    private var mLoginChanged = false
    private var mLoginUser: LoginUser? = null

    override fun getContentView(): Int {
        return R.layout.activity_setting
    }

    override fun initData(p0: Bundle?) {
        setPresenter(SettingPresenter())
        mIsLogin = mPresenter.isLogin()
    }

    override fun initView() {
        tv_setting_login.setOnClickListener(this)
        tv_setting_reload_api.setOnClickListener(this)
        s_setting_img.setOnCheckedChangeListener(this)
        s_setting_proxy.setOnCheckedChangeListener(this)

        s_setting_img.isChecked = mPresenter.getNoImg()
        s_setting_proxy.isChecked = mPresenter.getProxy()
        if (mIsLogin) {
            tv_setting_login.text = "用户登出"
        }else {
            tv_setting_login.text = "用户登录"
        }
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_setting_login -> {
                if (mIsLogin) {
                    mPresenter.requestLogout()
                }else {
                    startActivityForResult(Intent(this, LoginActivity::class.java),
                            SETTING_LOGIN)
                }
            }
            R.id.tv_setting_reload_api -> {
                mPresenter.requestReloadApi()
            }
        }
    }

    override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
        when (p0!!.id) {
            R.id.s_setting_img -> {
                mPresenter.setNoImg(p1)
            }
            R.id.s_setting_proxy -> {
                mPresenter.setProxy(p1)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SETTING_LOGIN && resultCode == Activity.RESULT_OK) {
            mLoginChanged = true
            mLoginUser = data!!.getParcelableExtra("loginUser")
            tv_setting_login.text = "用户登出"
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onBackPressed() {
        if (mLoginChanged) {
            val intent = Intent()
            if (mLoginUser == null) {
                intent.putExtra("result", RESULT_LOGOUT)
            }else {
                intent.putExtra("result", RESULT_LOGIN)
                intent.putExtra("user", mLoginUser)
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }else {
            super.onBackPressed()
        }
    }

    override fun onResultLogout(result: Boolean, info: String) {
        if (result) {
            mLoginChanged = true
            mLoginUser = null
            tv_setting_login.text = "用户登录"
        }else {
            toast(info)
        }
    }

    override fun onResultReloadApi(result: Boolean, info: String) {
        toast(info)
    }
}