package com.wuruoye.news.model.util

import android.content.Context
import android.content.Intent
import com.wuruoye.library.util.ResourceUtil
import com.wuruoye.news.R

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
}