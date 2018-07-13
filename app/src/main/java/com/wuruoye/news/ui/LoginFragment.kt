package com.wuruoye.news.ui

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.wuruoye.library.ui.WBaseFragment
import com.wuruoye.news.R
import com.wuruoye.news.contract.LoginContract
import com.wuruoye.news.contract.LoginLoginContract
import com.wuruoye.news.model.bean.LoginUser
import com.wuruoye.news.presenter.LoginPresenter
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * @Created : wuruoye
 * @Date : 2018/7/12 20:09.
 * @Description :
 */
class LoginFragment : WBaseFragment<LoginLoginContract.Presenter>(),
        LoginLoginContract.View, View.OnClickListener {
    override fun getContentView(): Int {
        return R.layout.fragment_login
    }

    override fun initData(p0: Bundle?) {
        setPresenter(LoginPresenter())
    }

    override fun initView(p0: View?) {
        p0!!.post {
            btn_login_login.setOnClickListener(this)
            btn_login_register.setOnClickListener(this)
        }
    }

    private fun onLoginClick() {
        val id = et_login_user.text.toString()
        val pwd = et_login_pwd.text.toString()

        if (id.length > 16) {
            til_login_user.error = "账号过长"
            return
        }
        if (pwd.length < 6) {
            til_login_pwd.error = "密码长度应大于6"
            return
        }

        mPresenter.requestLogin(id, pwd)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btn_login_login -> {
                onLoginClick()
            }
            R.id.btn_login_register -> {
                val view = activity as LoginContract.View
                view.goToRegister()
            }
        }
    }

    override fun onResultLogin(loginUser: LoginUser) {
        val intent = Intent()
        intent.putExtra("loginUser", loginUser)
        val view = activity as LoginContract.View
        view.setLoginResult(RESULT_OK, intent)
    }

    override fun onResultLoginInfo(info: String) {
        Toast.makeText(context, info, Toast.LENGTH_SHORT).show()
    }
}