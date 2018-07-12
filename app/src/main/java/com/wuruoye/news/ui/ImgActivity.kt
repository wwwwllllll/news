package com.wuruoye.news.ui

import android.os.Bundle
import android.widget.ImageView
import com.github.chrisbanes.photoview.PhotoView
import com.wuruoye.library.ui.WBaseActivity
import com.wuruoye.news.R
import com.wuruoye.news.adapter.ImgVPAdapter
import com.wuruoye.news.contract.ImgContract
import kotlinx.android.synthetic.main.activity_img.*

/**
 * @Created : wuruoye
 * @Date : 2018/7/12 08:58.
 * @Description :
 */
class ImgActivity : WBaseActivity<ImgContract.Presenter>(), ImgContract.View {
    private lateinit var mImgList: List<String>
    private var mPosition: Int = 0

    override fun getContentView(): Int {
        return R.layout.activity_img
    }

    override fun initData(p0: Bundle?) {
        mImgList = p0!!.getStringArrayList("img")
        mPosition = p0.getInt("position")
    }

    override fun initView() {
        val imgList = arrayListOf<ImageView>()
        for (s in mImgList) {
            val photoView = PhotoView(this)
            photoView.setOnOutsidePhotoTapListener {
                onBackPressed()
            }
            photoView.setOnClickListener {
                onBackPressed()
            }
            imgList.add(photoView)
        }
        val adapter = ImgVPAdapter(imgList)
        vp_img.adapter = adapter
        vp_img.setCurrentItem(mPosition, false)
    }
}