package com.wuruoye.news.model.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import com.wuruoye.library.model.WConfig
import com.wuruoye.library.util.ResourceUtil
import com.wuruoye.news.R
import java.io.File

/**
 * @Created : wuruoye
 * @Date : 2018/7/16 10:18.
 * @Description :
 */
object ShareUtil {
    fun shareText(context: Context, text: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/*"
        intent.putExtra(Intent.EXTRA_TITLE, ResourceUtil.getString(context, R.string.app_name))
        intent.putExtra(Intent.EXTRA_TEXT, text)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    fun shareImg(context: Context, path: String) {
        val file = File(path)
        val uri = FileProvider.getUriForFile(context, WConfig.PROVIDER_AUTHORITY, file)

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_STREAM, uri)

        val resInfoList = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        for (resolveInfo in resInfoList) {
            loge(resolveInfo.activityInfo.packageName)
            val packageName = resolveInfo.activityInfo.packageName
            context.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        context.startActivity(intent)
    }

    fun shareImg(context: Context, bitmap: Bitmap) {
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap,
                "分享", ResourceUtil.getString(context, R.string.app_name))
        val uri = Uri.parse(path)
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        context.startActivity(intent)
    }
}