package com.wuruoye.news.ui

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.wuruoye.library.adapter.WBaseRVAdapter
import com.wuruoye.library.ui.WBaseFragment
import com.wuruoye.library.util.ResourceUtil
import com.wuruoye.news.R
import com.wuruoye.news.adapter.ArticleItemRVAdapter
import com.wuruoye.news.contract.ArticleListContract
import com.wuruoye.news.contract.MainContract
import com.wuruoye.news.model.Config.HOME_DETAIL
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
        WBaseRVAdapter.OnItemClickListener<ArticleItem>, ArticleItemRVAdapter.OnActionListener,
        SwipeRefreshLayout.OnRefreshListener {
    companion object {
        val TYPE_ARTICLE = "1"
        val TYPE_VIDEO = "4"
    }
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
            srl_list.setColorSchemeColors(ResourceUtil.getColor(context, R.color.activateColor))
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == HOME_DETAIL && resultCode == RESULT_OK) {
            val view = activity as MainContract.View
            view.onLogin()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRefresh() {
        mPage = "1"
        mPresenter.requestList(mCategory, mItem.url, mPage)
    }

    override fun onItemClick(p0: ArticleItem?) {
        when (p0!!.open) {
            TYPE_ARTICLE -> {
                if (!mPresenter.getWeb()) {
                    val bundle = Bundle()
                    bundle.putParcelable("article", p0)
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtras(bundle)
                    startActivityForResult(intent, HOME_DETAIL)
                }else {
                    val bundle = Bundle()
                    bundle.putString("url", p0.url)
                    bundle.putString("title", p0.title)
                    val intent = Intent(context, WebActivity::class.java)
                    intent.putExtras(bundle)
                    startActivity(intent)
                }
            }
            TYPE_VIDEO -> {
                val bundle = Bundle()
                bundle.putString("url", p0.url)
                bundle.putString("title", p0.title)
                val intent = Intent(context, VideoActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }
    }

    override fun onLoading(loadingView: View) {
        mLoadingView = loadingView
        mPresenter.requestList(mCategory, mItem.url, mPage)
    }

    override fun onResultList(articleList: ArticleList, add: Boolean) {
        srl_list.isRefreshing = false
        mPage = articleList.next
        setData(add, articleList.data)
    }
}