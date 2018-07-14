package com.wuruoye.news.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.wuruoye.library.adapter.WBaseRVAdapter
import com.wuruoye.library.ui.WBaseActivity
import com.wuruoye.library.ui.WBaseFragment
import com.wuruoye.news.R
import com.wuruoye.news.adapter.CollectionItemRVAdapter
import com.wuruoye.news.contract.CollectionContract
import com.wuruoye.news.model.bean.ArticleItem
import com.wuruoye.news.model.bean.CollectionList
import kotlinx.android.synthetic.main.activity_collect.*

class CollectionActivity: WBaseActivity<CollectionContract.Presenter>(), CollectionContract.View,
        CollectionItemRVAdapter.OnActionListner, WBaseRVAdapter.OnItemClickListener<ArticleItem>, View.OnClickListener{

    override fun initView() {
        collection_back_bt.setOnClickListener(this)
        mPresenter.GetList(mUserId,"",mpage)
        initRV()
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.collection_back_bt -> {
                onBackPressed()
            }
        }
    }

    private lateinit var mLoadingView: View
    private lateinit var mUserId: String
    private lateinit var mpage:String


    override fun GetCollectionList(collectionList: CollectionList) {
        mpage = collectionList.next
        setData(true,collectionList.data)
    }

    //将新闻传递给下一个页面
    override fun onItemClick(p0: ArticleItem?) {
        val bundles = Bundle()
        bundles.putParcelable("article",p0)
        val intent = Intent()
        intent.putExtras(bundles)
        startActivity(intent)
    }

    private fun setData(add:Boolean, list: List<ArticleItem>) {
        val adpter = collection_news_list.adapter as CollectionItemRVAdapter
        if (add) {
            adpter.addData(list)
        }else{
            adpter.data = list
        }
    }

    override fun OnLoading(loadingview: View) {
        mLoadingView = loadingview
        mPresenter.GetList(mUserId,"",mpage)
    }

    override fun getContentView(): Int {
        return R.layout.activity_collect
    }

    override fun initData(p0: Bundle?) {
        mUserId = p0?.getString("userid") ?: ""
        mpage = "1"
    }

    private fun initRV() {
        val adpter = CollectionItemRVAdapter()
        adpter.setOnItemClickListener(this)
        adpter.setOnActionListener(this)
        collection_news_list.adapter = adpter
        val manager = LinearLayoutManager(this)
        collection_news_list.layoutManager = manager
    }
}