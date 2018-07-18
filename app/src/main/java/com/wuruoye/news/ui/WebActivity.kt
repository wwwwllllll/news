package com.wuruoye.news.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.PopupMenu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.LinearLayout
import com.wuruoye.library.ui.WBaseActivity
import com.wuruoye.news.R
import com.wuruoye.news.contract.WebContract
import com.wuruoye.news.model.util.ShareUtil
import kotlinx.android.synthetic.main.activity_web.*

/**
 * @Created : wuruoye
 * @Date : 2018/7/12 09:20.
 * @Description :
 */
class  WebActivity : WBaseActivity<WebContract.Presenter>(), WebContract.View,
        View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    private lateinit var mUrl: String
    private lateinit var mTitle: String

    private lateinit var wvWeb: WebView
    private lateinit var pmMenu: PopupMenu

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
        iv_web_menu.setOnClickListener(this)
        tv_web_title.text = mTitle

        initDlg()

        wvWeb = WebView(applicationContext)
        wvWeb.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)

        val setting = wvWeb.settings
        with(setting) {
            cacheMode = WebSettings.LOAD_NO_CACHE
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            useWideViewPort = true
            loadWithOverviewMode = true
            setSupportZoom(false)
            displayZoomControls = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
        }

        wvWeb.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url!!.startsWith("http")) {
                    view?.loadUrl(url)
                }
                return true
            }

            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                handler?.proceed()
                super.onReceivedSslError(view, handler, error)
            }
        }

        wvWeb.webChromeClient = object : WebChromeClient() {
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

        ll_web.addView(wvWeb)
        wvWeb.loadUrl(mUrl.split("?")[0])
    }

    private fun initDlg() {
        pmMenu = PopupMenu(this, iv_web_menu, R.style.DlgTheme)
        pmMenu.menuInflater.inflate(R.menu.menu_web, pmMenu.menu)
        pmMenu.setOnMenuItemClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.iv_web_back -> {
                onBackPressed()
            }
            R.id.iv_web_menu -> {
                pmMenu.show()
            }
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.menu_web_share -> {
                ShareUtil.shareText(this, mUrl)
            }
            R.id.menu_web_close -> {
                finish()
            }
            R.id.menu_web_refresh -> {
                wvWeb.reload()
            }
            R.id.menu_web_browser -> {
                val intent = Intent(Intent.ACTION_VIEW)
                val uri = Uri.parse(mUrl)
                intent.data = uri
                startActivity(intent)
            }
        }
        return true
    }

    override fun onBackPressed() {
        if (wvWeb.canGoBack()) {
            wvWeb.goBack()
        }else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        wvWeb.loadDataWithBaseURL(null, "", "text/html",
                "utf-8", null)
        wvWeb.clearHistory()
        ll_web.removeView(wvWeb)
        wvWeb.destroy()
        super.onDestroy()
    }
}