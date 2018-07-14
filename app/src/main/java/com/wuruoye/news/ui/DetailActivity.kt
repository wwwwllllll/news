package com.wuruoye.news.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.wuruoye.library.ui.WBaseActivity
import com.wuruoye.news.R
import com.wuruoye.news.adapter.CommentRVAdapter
import com.wuruoye.news.contract.DetailContract
import com.wuruoye.news.model.bean.ArticleComment
import com.wuruoye.news.model.bean.ArticleDetail
import com.wuruoye.news.model.bean.ArticleInfo
import com.wuruoye.news.model.bean.ArticleItem
import com.wuruoye.news.model.bean.DetailItem.Companion.TYPE_H1
import com.wuruoye.news.model.bean.DetailItem.Companion.TYPE_IMG
import com.wuruoye.news.model.bean.DetailItem.Companion.TYPE_TEXT
import com.wuruoye.news.model.bean.DetailItem.Companion.TYPE_TEXT_CEN
import com.wuruoye.news.model.util.loge
import com.wuruoye.news.model.util.toast
import com.wuruoye.news.presenter.DetailPresenter
import kotlinx.android.synthetic.main.activity_detail.*

/**
 * @Created : wuruoye
 * @Date : 2018/7/11 21:47.
 * @Description :
 */
class DetailActivity : WBaseActivity<DetailContract.Presenter>(),
        DetailContract.View, View.OnClickListener, CommentRVAdapter.OnActionListener {
    private lateinit var mArticle: ArticleItem
    private val mImgList = arrayListOf<String>()

    private lateinit var mCommentCallback: CommentRVAdapter.OnActionCallback
    private lateinit var mPraiseCallback: CommentRVAdapter.OnActionCallback
    private var mLoadCallback: CommentRVAdapter.OnActionCallback? = null
    private var mCommentParent = 0

    private lateinit var dlgComment: AlertDialog
    private lateinit var tvCommentParent: TextView

    private val mDetailCommentCallback = object : CommentRVAdapter.OnActionCallback {
        override fun onPraise(add: Boolean) {

        }

        override fun onComment(add: Boolean) {
            if (add) {

            }
        }

        override fun onNoMore() {

        }

    }

    override fun getContentView(): Int {
        return R.layout.activity_detail
    }

    override fun initData(p0: Bundle?) {
        mArticle = p0!!.getParcelable("article")

        setPresenter(DetailPresenter())
    }

    override fun initView() {
        tv_detail_title.text = mArticle.title
        iv_detail_back.setOnClickListener(this)
        iv_detail_comment.setOnClickListener(this)
        iv_detail_praise.setOnClickListener(this)
        iv_detail_collect.setOnClickListener(this)

        initDlg()

        mPresenter.requestDetail("sina", "", mArticle.url)
        mPresenter.requestArticleInfo(mArticle.id)
    }

    @SuppressLint("InflateParams")
    private fun initDlg() {
        val view = LayoutInflater.from(this)
                .inflate(R.layout.dlg_comment, null)
        val et = view.findViewById<EditText>(R.id.et_dlg_comment)
        tvCommentParent = view.findViewById(R.id.tv_dlg_comment_parent)

        dlgComment = AlertDialog.Builder(this)
                .setTitle("写评论")
                .setView(view)
                .setPositiveButton("提交") {_, _ ->
                    val content = et.text.toString()
                    val article = mArticle.id
                    val parent = mCommentParent
                    mPresenter.requestComment(article, content, parent)
                }
                .setNegativeButton("取消") {_, _ ->

                }
                .setCancelable(false)
                .create()
    }

    private fun initRV() {
        val adapter = CommentRVAdapter()
        adapter.setOnActionListener(this)
        rv_detail.adapter = adapter
        rv_detail.layoutManager = LinearLayoutManager(this)
    }

    override fun onResultDetail(detail: ArticleDetail) {
        tv_detail_comment.text = "新闻评论"
        initRV()
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
            R.id.iv_detail_comment -> {
                mCommentCallback = mDetailCommentCallback
                mCommentParent = 0
                tvCommentParent.visibility = View.GONE
                dlgComment.show()
            }
            R.id.iv_detail_praise -> {
                mPresenter.requestPraiseArticle(mArticle.id)
            }
            R.id.iv_detail_collect -> {
                mPresenter.requestCollectArticle(mArticle.id, mArticle)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCommentClick(callback: CommentRVAdapter.OnActionCallback,
                                item: ArticleComment) {
        mCommentCallback = callback
        mCommentParent = item.id
        tvCommentParent.visibility = View.VISIBLE
        tvCommentParent.text = "@${item.user.name}: ${item.content}"
        dlgComment.show()
    }

    override fun onLoading(callback: CommentRVAdapter.OnActionCallback) {
        mLoadCallback = callback
        mPresenter.requestCommentList(mArticle.id)
    }

    override fun onPraiseClick(callback: CommentRVAdapter.OnActionCallback,
                               item: ArticleComment) {
        mPraiseCallback = callback
        mPresenter.requestPraiseComment(item.id)
    }

    override fun onResultCommentComment(comment: ArticleComment) {
        mCommentCallback.onComment(true)
        val adapter = rv_detail.adapter as CommentRVAdapter
        adapter.addDataHead(comment)
        rv_detail.post {
            rv_detail.smoothScrollToPosition(0)
        }
    }

    override fun onResultCommentComment(info: String) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show()
    }

    override fun onResultCommentList(commentList: List<ArticleComment>) {
        if (commentList.isEmpty() && mLoadCallback != null) {
            mLoadCallback!!.onNoMore()
        }else {
            val adapter = rv_detail.adapter as CommentRVAdapter
            adapter.addData(commentList)
        }
    }

    override fun onResultCommentList(info: String) {
        mLoadCallback?.onNoMore()
        toast(info)
    }

    override fun onResultPraiseComment(result: Boolean, info: String) {
        if (!result) {
            toast(info)
        }else {
            mPraiseCallback.onPraise(info == "UP")
        }
    }

    override fun onResultArticleInfo(info: ArticleInfo) {
        tv_detail_praise_num.text = info.praise_num.toString()
        tv_detail_comment_num.text = info.comment_num.toString()
        tv_detail_collect_num.text = info.collect_num.toString()

        iv_detail_praise.setImageResource(
                if (info.is_praise) R.drawable.ic_heart_full else R.drawable.ic_heart_not)
        iv_detail_collect.setImageResource(
                if (info.is_collect) R.drawable.ic_collect_full else R.drawable.ic_collect_not)
    }

    override fun onResultArticleInfo(info: String) {
        toast(info)
    }

    override fun onResultCollectArticle(result: Boolean, info: String) {
        if (result) {
            val up = info == "UP"
            iv_detail_collect.setImageResource(
                    if (up) R.drawable.ic_collect_full else R.drawable.ic_collect_not)
            tv_detail_collect_num.text = (tv_detail_collect_num.text.toString().toInt()
                    + if (up) 1 else -1).toString()
        }else {
            toast(info)
        }
    }

    override fun onResultPraiseArticle(result: Boolean, info: String) {
        if (result) {
            val up = info == "UP"
            iv_detail_praise.setImageResource(
                    if (up) R.drawable.ic_heart_full else R.drawable.ic_heart_not)
            tv_detail_praise_num.text = (tv_detail_praise_num.text.toString().toInt()
                    + if (up) 1 else -1).toString()
        }else {
            toast(info)
        }
    }
}