package com.wuruoye.news.model.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

/**
 * @Created : wuruoye
 * @Date : 2018/7/17 15:11.
 * @Description :
 */
object ClipboardUtil {
    fun clipText(context: Context, text: String) {
        val manager = context.getSystemService(Context.CLIPBOARD_SERVICE)
                as ClipboardManager
        val data = ClipData.newPlainText("label", text)
        manager.primaryClip = data
    }
}