package com.wuruoye.news.presenter

import com.wuruoye.library.model.Listener
import com.wuruoye.library.model.WConfig
import com.wuruoye.library.util.FileUtil
import com.wuruoye.library.util.net.WNet
import com.wuruoye.news.contract.ImgContract

/**
 * @Created : wuruoye
 * @Date : 2018/7/17 15:21.
 * @Description :
 */
class ImgPresenter : ImgContract.Presenter() {
    override fun getFilePath(name: String): String {
        val path = "${WConfig.IMAGE_PATH}${System.currentTimeMillis()}.jpg"
        FileUtil.checkFile(path)
        return path
    }

    override fun downloadFile(path: String) {
        val filePath = getFilePath(path)
        WNet.downloadFileInBackground(path, filePath, object : Listener<String> {
            override fun onSuccessful(p0: String?) {
                view?.onResultDownload(true, "已保存在 $filePath")
            }

            override fun onFail(p0: String?) {
                view?.onResultDownload(false, p0!!)
            }
        })
    }
}