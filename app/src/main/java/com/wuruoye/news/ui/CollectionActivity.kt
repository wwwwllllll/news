package com.wuruoye.news.ui

import android.os.Bundle
import android.view.View
import com.wuruoye.library.ui.WBaseActivity
import com.wuruoye.news.R
import com.wuruoye.news.contract.CollectionContract

class CollectionActivity: WBaseActivity<CollectionContract.Presenter>(), CollectionContract.View, View.OnClickListener{

    override fun onClick(p0: View?) {

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getContentView(): Int {
        return R.layout.activity_collect
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initData(p0: Bundle?) {

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView() {

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}