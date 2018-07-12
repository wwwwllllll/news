package com.wuruoye.news.ui

import android.os.Bundle
import android.view.View.GONE
import android.view.WindowManager
import com.wuruoye.library.ui.WBaseActivity
import com.wuruoye.news.R
import com.wuruoye.news.contract.VideoContract
import kotlinx.android.synthetic.main.activity_video.*

/**
 * @Created : wuruoye
 * @Date : 2018/7/12 15:00.
 * @Description :
 */
class VideoActivity : WBaseActivity<VideoContract.Presenter>(), VideoContract.View {
    private lateinit var mUrl: String
    private lateinit var mTitle: String

    override fun getContentView(): Int {
        return R.layout.activity_video
    }

    override fun initData(p0: Bundle?) {
        mUrl = p0!!.getString("url")
        mTitle = try {
            p0.getString("title")
        }catch (e: Exception) {
            ""
        }
    }

    override fun initView() {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        with(sp_video) {
            initUIState()
            setUp(mUrl, true, mTitle)
            isShowFullAnimation = false
            isRotateViewAuto = true
            isRotateWithSystem = false
            isNeedShowWifiTip = false
            startWindowFullscreen(applicationContext, false, false)
            fullscreenButton.visibility = GONE
        }
    }

    override fun onDestroy() {
        sp_video.destroyDrawingCache()
        super.onDestroy()
    }
}