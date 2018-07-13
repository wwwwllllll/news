package com.wuruoye.news.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.wuruoye.library.ui.WBaseFragment
import com.wuruoye.news.R
import com.wuruoye.news.contract.LoginContract
import com.wuruoye.news.contract.LoginRegisterContract
import com.wuruoye.news.presenter.RegisterPresenter
import kotlinx.android.synthetic.main.fragment_register.*

/**
 * @Created : wuruoye
 * @Date : 2018/7/12 20:10.
 * @Description :
 */
class RegisterFragment : WBaseFragment<LoginRegisterContract.Presenter>(),
        LoginRegisterContract.View {
    override fun getContentView(): Int {
        return R.layout.fragment_register
    }

    override fun initData(p0: Bundle?) {
        setPresenter(RegisterPresenter())
    }

    override fun initView(p0: View?) {
        p0!!.post {
            btn_register.setOnClickListener {
                onRegisterClick()
            }
        }
    }

    private fun onRegisterClick() {
        val id = et_register_id.text.toString()
        val nickname = et_register_nickname.text.toString()
        val pwd = et_register_pwd.text.toString()
        val email = et_register_email.text.toString()

        if (id.length > 16 || id.length < 6) {
            til_register_id.error = "账号长度应在 6-16 之间"
            return
        }
        if (nickname.length > 10 || nickname.length < 2) {
            til_register_nickname.error = "昵称长度应在 2-10 之间"
            return
        }
        if (pwd.length < 6) {
            til_register_pwd.error = "密码长度应大于 6"
            return
        }
        if (email.isEmpty()) {
            til_register_email.error = "邮箱不能为空"
            return
        }

        mPresenter.requestRegister(id, nickname, pwd, email)
    }

    override fun onResultRegister(ok: Boolean, info: String) {
        Toast.makeText(context, info, Toast.LENGTH_SHORT).show()
        if (ok) {
            et_register_id.text.clear()
            et_register_nickname.text.clear()
            et_register_pwd.text.clear()
            et_register_email.text.clear()
            val view = activity as LoginContract.View
            view.goToLogin()
        }
    }
}