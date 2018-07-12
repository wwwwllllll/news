package com.wuruoye.news.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.wuruoye.library.ui.WBaseActivity
import com.wuruoye.news.R
import com.wuruoye.news.contract.DetailContract
import com.wuruoye.news.model.bean.ArticleDetail
import com.wuruoye.news.model.bean.ArticleItem
import com.wuruoye.news.model.bean.DetailItem.Companion.TYPE_H1
import com.wuruoye.news.model.bean.DetailItem.Companion.TYPE_IMG
import com.wuruoye.news.model.bean.DetailItem.Companion.TYPE_TEXT
import com.wuruoye.news.model.bean.DetailItem.Companion.TYPE_TEXT_CEN
import com.wuruoye.news.presenter.DetailPresenter
import kotlinx.android.synthetic.main.activity_detail.*

/**
 * @Created : wuruoye
 * @Date : 2018/7/11 21:47.
 * @Description :
 */
class DetailActivity : WBaseActivity<DetailContract.Presenter>(),
        DetailContract.View, View.OnClickListener {
    private lateinit var mArticle: ArticleItem
    private val mImgList = arrayListOf<String>()

    override fun getContentView(): Int {
        return R.layout.activity_detail
    }

    override fun initData(p0: Bundle?) {
        mArticle = p0!!.getParcelable("article")

        setPresenter(DetailPresenter())
    }

    override fun initView() {
        iv_detail_back.setOnClickListener(this)
        tv_detail_title.text = mArticle.title

        mPresenter.requestDetail("sina", "", mArticle.url)
    }

    override fun onResultDetail(detail: ArticleDetail) {
        val items = detail.data
        if (items.isEmpty()) {
            onNoData()
        }
        for (item in items) {
            when (item.type) {
                TYPE_TEXT -> {
                    val text = LayoutInflater.from(this)
                            .inflate(R.layout.view_text, ll_detail_content, false)
                            as TextView
                    ll_detail_content.addView(text)

                    text.text = item.info
                }
                TYPE_TEXT_CEN -> {
                    val text = LayoutInflater.from(this)
                            .inflate(R.layout.view_text_cen, ll_detail_content, false)
                            as TextView
                    ll_detail_content.addView(text)

                    text.text = item.info
                }
                TYPE_IMG -> {
                    val image = LayoutInflater.from(this)
                            .inflate(R.layout.view_image, ll_detail_content, false)
                            as ImageView
                    ll_detail_content.addView(image)

                    Glide.with(image)
                            .load(item.info)
                            .into(image)
                    mImgList.add(item.info)
                    image.setOnClickListener {
                        val bundle = Bundle()
                        bundle.putStringArrayList("img", mImgList)
                        bundle.putInt("position", mImgList.indexOf(item.info))
                        val intent = Intent(this, ImgActivity::class.java)
                        intent.putExtras(bundle)
                        startActivity(intent)
                    }
                }
                TYPE_H1 -> {
                    val h1 = LayoutInflater.from(this)
                            .inflate(R.layout.view_h1, ll_detail_content, false)
                            as TextView
                    ll_detail_content.addView(h1)

                    h1.text = item.info
                }
            }
        }
    }

    private fun onNoData() {
        loge(mArticle.url)
        AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("后台解析失败，是否打开网页？")
                .setPositiveButton("确定") { _, _ ->
                    val bundle = Bundle()
                    bundle.putString("url", mArticle.url)
                    bundle.putString("title", mArticle.title)
                    val intent = Intent(this, WebActivity::class.java)
                    intent.putExtras(bundle)
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("取消") { _, _ ->
                    finish()
                }
                .setCancelable(false)
                .show()
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.iv_detail_back -> {
                onBackPressed()
            }
        }
    }

    fun loge(message: String) {
        Log.e("DetailActivity", message)
    }
}