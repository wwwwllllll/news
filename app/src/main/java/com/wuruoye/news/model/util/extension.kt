package com.wuruoye.news.model.util

import android.content.Context
import android.util.Log
import android.widget.Toast

/**
 * @Created : wuruoye
 * @Date : 2018/7/14 09:11.
 * @Description :
 */

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Any.loge(message: String) {
    Log.e(this.toString(), message)
}