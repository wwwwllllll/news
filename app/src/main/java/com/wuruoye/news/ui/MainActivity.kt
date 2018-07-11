package com.wuruoye.news.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import com.google.gson.JsonObject
import com.wuruoye.library.adapter.FragmentVPAdapter
import com.wuruoye.library.ui.WBaseActivity
import com.wuruoye.news.R
import com.wuruoye.news.contract.MainContract
import com.wuruoye.news.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @Created : wuruoye
 * @Date : 2018/7/9 12:04.
 * @Description : 主页
 */
class MainActivity : WBaseActivity<MainContract.Presenter>(), MainContract.View {
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
        list.add(home)
        list.add(user)
        val adapter = FragmentVPAdapter(supportFragmentManager, arrayListOf(), list)
        vp_main.adapter = adapter
        ctl_main.attachViewPager(vp_main)
    }

    override fun onResultApi() {
        initVP()
    }
}