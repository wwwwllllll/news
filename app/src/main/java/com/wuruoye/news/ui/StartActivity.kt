package com.wuruoye.news.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import com.wuruoye.library.ui.WBaseActivity
import com.wuruoye.news.R
import com.wuruoye.news.contract.StartContract
import com.wuruoye.news.model.UserCache
import com.wuruoye.news.presenter.StartPresenter
import kotlinx.android.synthetic.main.activity_start.*

/**
 * @Created : wuruoye
 * @Date : 2018/7/13 11:23.
 * @Description :
 */
class StartActivity : WBaseActivity<StartContract.Presenter>(), StartContract.View {
    companion object {
        val TEXT_TRANSLATION = 600
    }
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

        mPresenter.checkLogin()
        initAnimator()
    }

    private fun initAnimator() {
        val valueAnimator = ValueAnimator.ofFloat(0F, 1F)
        valueAnimator.duration = 1500L
        valueAnimator.interpolator = AccelerateDecelerateInterpolator()
        valueAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                if (mLoginResult) {
                    goToMain()
                }else {
                    mTimeout = true
                }
            }
        })
        valueAnimator.addUpdateListener { p0 ->
            val value = p0!!.animatedValue as Float
            iv_start.scaleX = value * 0.5F + 1
            iv_start.scaleY = value * 0.5F + 1

            tv_start_name.scaleX = value * 0.5F + 1
            tv_start_name.scaleY = value * 0.5F + 1
            tv_start_name.translationY = - TEXT_TRANSLATION * value
        }
        valueAnimator.start()
    }

    override fun onResultCheck(login: Boolean) {
        if (mTimeout) {
            goToMain()
        }else {
            mLoginResult = true
        }
    }

    override fun getResources(): Resources {
        val resource = super.getResources()
        val configure = resource.configuration
        configure.fontScale = UserCache.getInstance().textSize
        resource.updateConfiguration(configure, resource.displayMetrics)
        return resource
    }

    private fun goToMain() {
        if (mPresenter.isLead()) {
            startActivity(Intent(this, MainActivity::class.java))
        }else {
            startActivity(Intent(this, LeadActivity::class.java))
        }
        finish()
    }
}