package com.wuruoye.news.ui

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.WindowManager
import android.widget.MediaController
import com.wuruoye.library.ui.WBaseActivity
import com.wuruoye.news.R
import com.wuruoye.news.contract.VideoContract
import kotlinx.android.synthetic.main.activity_video.*

/**
 * @Created : wuruoye
 * @Date : 2018/7/12 15:00.
 * @Description :
 */
class VideoActivity : WBaseActivity<VideoContract.Presenter>(), VideoContract.View,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {
    private lateinit var mUrl: String
    private lateinit var mTitle: String

    private lateinit var dlgLoading: AlertDialog
    private lateinit var dlgError: AlertDialog
    private lateinit var dlgComplete: AlertDialog

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

        initDlg()
        initVideo()
    }

    private fun initDlg() {
        dlgLoading = AlertDialog.Builder(this, R.style.DlgTheme)
                .setTitle("提示")
                .setView(R.layout.dlg_loading)
                .setNegativeButton("退出") {_, _ ->
                    finish()
                }
                .setCancelable(false)
                .create()
        dlgError = AlertDialog.Builder(this, R.style.DlgTheme)
                .setTitle("提示")
                .setMessage("视频播放出错，是否重新加载？")
                .setPositiveButton("确定") {_, _ ->
                    dlgLoading.show()
                    vv_video.setVideoURI(Uri.parse(mUrl))
                }
                .setNegativeButton("退出") {_, _ ->
                    finish()
                }
                .setCancelable(false)
                .create()
        dlgComplete = AlertDialog.Builder(this, R.style.DlgTheme)
                .setTitle("提示")
                .setMessage("视频播放完毕，是否再次播放？")
                .setPositiveButton("确定") {_, _ ->
                    vv_video.start()
                }
                .setNegativeButton("退出") {_, _ ->
                    finish()
                }
                .setCancelable(false)
                .create()
    }

    private fun initVideo() {
        dlgLoading.show()
        vv_video.setMediaController(MediaController(this))
        vv_video.setVideoURI(Uri.parse(mUrl))
        vv_video.setOnPreparedListener(this)
        vv_video.setOnErrorListener(this)
        vv_video.setOnCompletionListener(this)
    }

    override fun onPrepared(p0: MediaPlayer?) {
        dlgLoading.dismiss()
        vv_video.start()
    }

    override fun onError(p0: MediaPlayer?, p1: Int, p2: Int): Boolean {
        dlgError.show()
        return true
    }

    override fun onCompletion(p0: MediaPlayer?) {
        dlgComplete.show()
    }
}