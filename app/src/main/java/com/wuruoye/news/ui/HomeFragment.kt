package com.wuruoye.news.ui

import android.os.Bundle
import android.view.View
import com.wuruoye.library.ui.WBaseFragment
import com.wuruoye.news.R
import com.wuruoye.news.contract.HomeContract

/**
 * @Created : wuruoye
 * @Date : 2018/7/9 16:53.
 * @Description : 新闻显示页面
 */
class HomeFragment : WBaseFragment<HomeContract.Presenter>(), HomeContract.View {
    override fun getContentView(): Int {
        return R.layout.fragment_home
    }

    override fun initData(p0: Bundle?) {

    }

    override fun initView(p0: View?) {

    }
}