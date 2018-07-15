package com.wuruoye.news.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.wuruoye.library.adapter.WBaseRVAdapter
import com.wuruoye.library.ui.WBaseActivity
import com.wuruoye.news.R
import com.wuruoye.news.adapter.CollectRVAdapter
import com.wuruoye.news.contract.CollectContract
import com.wuruoye.news.model.bean.ArticleCollect
import com.wuruoye.news.model.bean.LoginUser
import com.wuruoye.news.model.util.toast
import com.wuruoye.news.presenter.CollectPresenter
import kotlinx.android.synthetic.main.activity_collect.*

/**
 * @Created : wuruoye
 * @Date : 2018/7/14 20:31.
 * @Description :
 */
class CollectActivity : WBaseActivity<CollectContract.Presenter>(), CollectContract.View,
        WBaseRVAdapter.OnItemClickListener<ArticleCollect>, CollectRVAdapter.OnActionListener,
        View.OnClickListener {
    private lateinit var mLoginUser: LoginUser
    private lateinit var mCancelItem: ArticleCollect
    private lateinit var mLoadCallback: CollectRVAdapter.OnActionCallback

    override fun getContentView(): Int {
        return R.layout.activity_collect
    }

    override fun initData(p0: Bundle?) {
        mLoginUser = p0!!.getParcelable("user")

        setPresenter(CollectPresenter())
    }

    override fun initView() {
        iv_collect_back.setOnClickListener(this)
        initRV()
    }

    private fun initRV() {
        val adapter = CollectRVAdapter()
        adapter.setOnItemClickListener(this)
        adapter.setOnActionListener(this)
        rv_collect.adapter = adapter
        rv_collect.layoutManager = LinearLayoutManager(this)
        rv_collect.isNestedScrollingEnabled = false
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.iv_collect_back -> {
                onBackPressed()
            }
        }
    }

    override fun onItemClick(p0: ArticleCollect?) {
        val bundle = Bundle()
        bundle.putParcelable("article", p0!!.content)
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onLoadMore(callback: CollectRVAdapter.OnActionCallback) {
        mLoadCallback = callback
        mPresenter.requestCollectList(mLoginUser.id)
    }

    override fun onCancelClick(item: ArticleCollect) {
        mCancelItem = item
        mPresenter.requestCancelCollect(item.id)
    }

    override fun onResultCollectList(collectList: List<ArticleCollect>) {
        if (collectList.isEmpty()) {
            mLoadCallback.onNoMore()
        }else {
            val adapter = rv_collect.adapter as CollectRVAdapter
            adapter.addData(collectList)
        }
    }

    override fun onResultCollectList(info: String) {
        mLoadCallback.onNoMore()
        toast(info)
    }

    override fun onResultCancelCollect(result: Boolean, info: String) {
        if (result) {
            val adapter = rv_collect.adapter as CollectRVAdapter
            adapter.removeData(mCancelItem)
        }else {
            toast(info)
        }
    }
}