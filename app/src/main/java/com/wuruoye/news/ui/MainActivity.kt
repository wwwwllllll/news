package com.wuruoye.news.ui

import android.os.Bundle
import android.support.v4.app.Fragment
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
    }

    override fun initView() {
        initVP()
    }

    private fun initVP() {
        val list = ArrayList<Fragment>()
        val home = HomeFragment()
        val user = UserFragment()
        list.add(home)
        list.add(user)
        val vpAdapter = FragmentVPAdapter(supportFragmentManager, arrayListOf(), list)
        vp_main.adapter = vpAdapter
        ctl_main.attachViewPager(vp_main)
    }
}