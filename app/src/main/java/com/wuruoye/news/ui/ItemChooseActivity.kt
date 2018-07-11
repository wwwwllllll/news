package com.wuruoye.news.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.wuruoye.library.ui.WBaseActivity
import com.wuruoye.news.R
import com.wuruoye.news.adapter.ApiRVAdapter
import com.wuruoye.news.adapter.TitleRVAdapter
import com.wuruoye.news.contract.ItemChooseContract
import com.wuruoye.news.presenter.ItemChoosePresenter
import kotlinx.android.synthetic.main.activity_item_choose.*

/**
 * @Created : wuruoye
 * @Date : 2018/7/11 12:18.
 * @Description :
 */
class ItemChooseActivity : WBaseActivity<ItemChooseContract.Presenter>(),
        ItemChooseContract.View, View.OnClickListener, ApiRVAdapter.OnActionListener {
    override fun getContentView(): Int {
        return R.layout.activity_item_choose
    }

    override fun initData(p0: Bundle?) {
        setPresenter(ItemChoosePresenter())
    }

    override fun initView() {
        srl_item_choose.isEnabled = false
        iv_item_choose_back.setOnClickListener(this)

        initRV()
    }

    private fun initRV() {
        val adapter = ApiRVAdapter()
        adapter.data = mPresenter.getApis()
        adapter.setOnActionListener(this)
        rv_item_choose.adapter = adapter
        rv_item_choose.layoutManager = LinearLayoutManager(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.iv_item_choose_back -> {
                onBackPressed()
            }
        }
    }

    override fun onItemClick(category: String) {
        val data = Intent()
        data.putExtra("category", category)
        setResult(Activity.RESULT_OK, data)
        finish()
    }
}