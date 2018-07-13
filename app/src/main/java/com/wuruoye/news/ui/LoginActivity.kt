package com.wuruoye.news.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.wuruoye.library.adapter.FragmentVPAdapter
import com.wuruoye.library.ui.WBaseActivity
import com.wuruoye.news.R
import com.wuruoye.news.contract.LoginContract
import kotlinx.android.synthetic.main.activity_login.*

/**
 * @Created : wuruoye
 * @Date : 2018/7/12 20:06.
 * @Description :
 */
class LoginActivity : WBaseActivity<LoginContract.Presenter>(), LoginContract.View,
        View.OnClickListener {
    companion object {
        val TITLE = arrayListOf("登录", "注册")
        const val ITEM_LOGIN = 0
        const val ITEM_REGISTER = 1
    }

    override fun getContentView(): Int {
        return R.layout.activity_login
    }

    override fun initData(p0: Bundle?) {

    }

    override fun initView() {
        iv_login_back.setOnClickListener(this)

        initVP()
    }

    private fun initVP() {
        val fragments = arrayListOf<Fragment>()
        fragments.add(LoginFragment())
        fragments.add(RegisterFragment())
        val adapter = FragmentVPAdapter(supportFragmentManager, TITLE, fragments)
        vp_login.adapter = adapter
        tl_login.setupWithViewPager(vp_login)
    }

    override fun goToRegister() {
        vp_login.currentItem = ITEM_REGISTER
    }

    override fun goToLogin() {
        vp_login.currentItem = ITEM_LOGIN
    }

    override fun setLoginResult(result: Int, data: Intent) {
        setResult(result, data)
        finish()
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.iv_login_back -> {
                onBackPressed()
            }
        }
    }
}