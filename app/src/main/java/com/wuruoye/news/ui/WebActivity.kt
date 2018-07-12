package com.wuruoye.news.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import com.wuruoye.library.ui.WBaseActivity
import com.wuruoye.news.R
import com.wuruoye.news.contract.WebContract
import kotlinx.android.synthetic.main.activity_web.*

/**
 * @Created : wuruoye
 * @Date : 2018/7/12 09:20.
 * @Description :
 */
class WebActivity : WBaseActivity<WebContract.Presenter>(), WebContract.View, View.OnClickListener {
    private lateinit var mUrl: String
    private lateinit var mTitle: String

    private lateinit var wv_web: WebView

    override fun getContentView(): Int {
        return R.layout.activity_web
    }

    override fun initData(p0: Bundle?) {
        mUrl = p0!!.getString("url")
        mTitle = try {
            p0.getString("title")
        } catch (e: Exception) {
            ""
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView() {
        iv_web_back.setOnClickListener(this)
        tv_web_title.text = mTitle
        iv_web_close.setOnClickListener(this)
        iv_web_refresh.setOnClickListener(this)

        wv_web = WebView(applicationContext)
        wv_web.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)

        val setting = wv_web.settings
        with(setting) {
            javaScriptEnabled = true
            useWideViewPort = true
            loadWithOverviewMode = true
            setSupportZoom(false)
            displayZoomControls = false
        }

        wv_web.webViewClient = object : WebViewClient() {
//            override fun shouldOverrideUrlLoading(view: WebView?, request:
//                      WebResourceRequest?): Boolean {
//                view?.loadUrl(request!!.url.toString())
//                return true
//            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url!!)
                return true
            }
        }

        wv_web.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                pb_web.progress = newProgress
                super.onProgressChanged(view, newProgress)
            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                if (mTitle.isEmpty()) {
                    tv_web_title.text = title
                }
                super.onReceivedTitle(view, title)
            }

            override fun onJsAlert(view: WebView?, url: String?, message: String?,
                                   result: JsResult?): Boolean {
                return super.onJsAlert(view, url, message, result)
            }
        }

        ll_web.addView(wv_web)
        wv_web.loadUrl(mUrl.split("?")[0])
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.iv_web_back -> {
                onBackPressed()
            }

            R.id.iv_web_refresh -> {
                wv_web.reload()
            }

            R.id.iv_web_close -> {
                finish()
            }
        }
    }

    override fun onBackPressed() {
        if (wv_web.canGoBack()) {
            wv_web.goBack()
        }else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        wv_web.loadDataWithBaseURL(null, "", "text/html",
                "utf-8", null)
        wv_web.clearHistory()
        ll_web.removeView(wv_web)
        wv_web.destroy()
        super.onDestroy()
    }
}