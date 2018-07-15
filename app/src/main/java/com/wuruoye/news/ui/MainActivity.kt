package com.wuruoye.news.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import com.google.gson.JsonObject
import com.wuruoye.library.adapter.FragmentVPAdapter
import com.wuruoye.library.ui.WBaseActivity
import com.wuruoye.news.R
import com.wuruoye.news.contract.MainContract
import com.wuruoye.news.contract.UserContract
import com.wuruoye.news.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @Created : wuruoye
 * @Date : 2018/7/9 12:04.
 * @Description : 主页
 */
class MainActivity : WBaseActivity<MainContract.Presenter>(), MainContract.View {
    private lateinit var mUserView: UserContract.View

    override fun getContentView(): Int {
        return R.layout.activity_main
    }

    override fun initData(p0: Bundle?) {
        setPresenter(MainPresenter())
        val obj = JsonObject()
        mPresenter.requestApi()
    }

    override fun initView() {
//        initVP()
    }

    private fun initVP() {
        val list = ArrayList<Fragment>()
        val home = HomeFragment()
        val user = UserFragment()
        mUserView = user
        list.add(home)
        list.add(user)
        val adapter = FragmentVPAdapter(supportFragmentManager, arrayListOf("首页", "用户"), list)
        vp_main.adapter = adapter
        ctl_main.attachViewPager(vp_main)
    }

    override fun onResultApi() {
        initVP()
    }

    override fun onLogin() {
        mUserView.onLogin()
    }
}