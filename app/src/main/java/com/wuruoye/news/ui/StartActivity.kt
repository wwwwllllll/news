package com.wuruoye.news.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.wuruoye.library.ui.WBaseActivity
import com.wuruoye.news.R
import com.wuruoye.news.contract.StartContract
import com.wuruoye.news.presenter.StartPresenter

/**
 * @Created : wuruoye
 * @Date : 2018/7/13 11:23.
 * @Description :
 */
class StartActivity : WBaseActivity<StartContract.Presenter>(), StartContract.View {
    private var mTimeout = false
    private var mLoginResult = false

    override fun getContentView(): Int {
        return R.layout.activity_start
    }

    override fun initData(p0: Bundle?) {
        setPresenter(StartPresenter())
    }

    override fun initView() {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        Handler().postDelayed(object : Runnable {
            override fun run() {
                if (mLoginResult) {
                    goToMain()
                }else {
                    mTimeout = true
                }
            }
        }, 1000)
        mPresenter.checkLogin()
    }

    override fun onResultCheck(login: Boolean) {
        if (mTimeout) {
            goToMain()
        }else {
            mLoginResult = true
        }
    }

    private fun goToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}