package com.wuruoye.news.ui

import android.os.Bundle
import com.wuruoye.library.ui.WBaseActivity
import com.wuruoye.news.contract.MainContract

/**
 * @Created : wuruoye
 * @Date : 2018/7/9 12:04.
 * @Description :
 */
class MainActivity : WBaseActivity<MainContract.Presenter>(), MainContract.View {
    override fun initView() {

    }

    override fun initData(p0: Bundle?) {

    }

    override fun getContentView(): Int {
        return 0
    }

}