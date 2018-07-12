package com.wuruoye.news.ui

import android.os.Bundle
import android.view.View
import com.wuruoye.library.ui.WBaseActivity
import com.wuruoye.news.R
import com.wuruoye.news.contract.DetailContract
import com.wuruoye.news.model.bean.ArticleItem
import kotlinx.android.synthetic.main.activity_detail.*

/**
 * @Created : wuruoye
 * @Date : 2018/7/11 21:47.
 * @Description :
 */
class DetailActivity : WBaseActivity<DetailContract.Presenter>(),
        DetailContract.View, View.OnClickListener {
    private lateinit var mArticle: ArticleItem

    override fun getContentView(): Int {
        return R.layout.activity_detail
    }

    override fun initData(p0: Bundle?) {
        mArticle = p0!!.getParcelable("article")
    }

    override fun initView() {
        iv_detail_back.setOnClickListener(this)
        tv_detail_title.text = mArticle.title
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.iv_detail_back -> {
                onBackPressed()
            }
        }
    }
}