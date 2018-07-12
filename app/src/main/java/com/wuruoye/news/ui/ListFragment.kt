package com.wuruoye.news.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.wuruoye.library.adapter.WBaseRVAdapter
import com.wuruoye.library.ui.WBaseFragment
import com.wuruoye.news.R
import com.wuruoye.news.adapter.ArticleItemRVAdapter
import com.wuruoye.news.contract.ArticleListContract
import com.wuruoye.news.model.bean.ArticleItem
import com.wuruoye.news.model.bean.ArticleList
import com.wuruoye.news.model.bean.Item
import com.wuruoye.news.presenter.ListPresenter
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * @Created : wuruoye
 * @Date : 2018/7/10 19:14.
 * @Description :
 */
class ListFragment : WBaseFragment<ArticleListContract.Presenter>(), ArticleListContract.View,
        WBaseRVAdapter.OnItemClickListener<ArticleItem>, ArticleItemRVAdapter.OnActionListener, SwipeRefreshLayout.OnRefreshListener {
    private lateinit var mItem: Item
    private lateinit var mCategory: String
    private lateinit var mPage: String
    private lateinit var mLoadingView: View

    override fun getContentView(): Int {
        return R.layout.fragment_list
    }

    override fun initData(p0: Bundle?) {
        mItem = p0!!.getParcelable("item")
        mCategory = p0.getString("category")
        mPage = "1"

        setPresenter(ListPresenter())
    }

    override fun initView(p0: View?) {
        p0?.post{
            srl_list.setOnRefreshListener(this)
            initRV()

//            onRefresh()
        }
    }

    private fun initRV() {
        val adapter = ArticleItemRVAdapter()
        adapter.setOnItemClickListener(this)
        adapter.setOnActionListener(this)
        rv_list.adapter = adapter
        rv_list.layoutManager = LinearLayoutManager(context)
    }

    private fun setData(add: Boolean, data: List<ArticleItem>) {
        val adapter = rv_list.adapter as ArticleItemRVAdapter
        if (add) {
            adapter.addData(data)
        }else {
            adapter.data = data
        }
    }

    override fun onRefresh() {
        mPage = "1"
        mPresenter.requestList(mCategory, mItem.url, mPage)
    }

    override fun onItemClick(p0: ArticleItem?) {
//        Toast.makeText(context, p0!!.title, Toast.LENGTH_SHORT).show()
        val bundle = Bundle()
        bundle.putParcelable("article", p0!!)
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onLoading(loadingView: View) {
        mLoadingView = loadingView
        mPresenter.requestList(mCategory, mItem.url, mPage)
    }

    override fun onResultList(articleList: ArticleList) {
        srl_list.isRefreshing = false
        mPage = articleList.next
        setData(true, articleList.data)
    }
}