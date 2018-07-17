package com.wuruoye.news.ui

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView
import com.wuruoye.library.ui.WBaseActivity
import com.wuruoye.news.R
import com.wuruoye.news.adapter.ImgVPAdapter
import com.wuruoye.news.contract.ImgContract
import com.wuruoye.news.model.util.ClipboardUtil
import com.wuruoye.news.model.util.ShareUtil
import com.wuruoye.news.model.util.toast
import com.wuruoye.news.presenter.ImgPresenter
import kotlinx.android.synthetic.main.activity_img.*


/**
 * @Created : wuruoye
 * @Date : 2018/7/12 08:58.
 * @Description :
 */
class ImgActivity : WBaseActivity<ImgContract.Presenter>(), ImgContract.View {
    companion object {
        val ITEM_TITLE = arrayOf("分享", "保存", "拷贝链接")
    }
    private lateinit var mImgList: List<String>
    private var mPosition: Int = 0

    override fun getContentView(): Int {
        return R.layout.activity_img
    }

    override fun initData(p0: Bundle?) {
        mImgList = p0!!.getStringArrayList("img")
        mPosition = try {
            p0.getInt("position")
        } catch (e: Exception) {
            0
        }

        setPresenter(ImgPresenter())
    }

    override fun initView() {
        // full screen
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val imgList = arrayListOf<ImageView>()
        for (s in mImgList) {
            val photoView = PhotoView(this)
            photoView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT)
            photoView.setOnOutsidePhotoTapListener {
                onBackPressed()
            }
            photoView.setOnClickListener {
                onBackPressed()
            }
            photoView.setOnLongClickListener {
                AlertDialog.Builder(this, R.style.DlgTheme)
                        .setTitle("选择操作")
                        .setItems(ITEM_TITLE) {_, position ->
                            when (position) {
                                0 -> {
                                    ShareUtil.shareImg(this, getBitmap(photoView.drawable))
                                }
                                1 -> {
                                    mPresenter.downloadFile(s)
                                }
                                2 -> {
                                    ClipboardUtil.clipText(this, s)
                                    toast("已复制 $s")
                                }
                            }
                        }
                        .show()
                true
            }
            imgList.add(photoView)
            Glide.with(photoView)
                    .load(s)
                    .into(photoView)
        }
        val adapter = ImgVPAdapter(imgList)
        vp_img.adapter = adapter
        vp_img.setCurrentItem(mPosition, false)
    }

    private fun getBitmap(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }else {
            val bitmap = Bitmap.createBitmap(
                    drawable.intrinsicWidth,
                    drawable.intrinsicHeight,
                    if (drawable.opacity != PixelFormat.OPAQUE)
                        Bitmap.Config.ARGB_8888
                    else
                        Bitmap.Config.RGB_565)
            val canvas = Canvas(bitmap)
            //canvas.setBitmap(bitmap);
            drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
            drawable.draw(canvas)
            return bitmap
        }
    }

    override fun onResultDownload(result: Boolean, info: String) {
        toast(info)
    }
}