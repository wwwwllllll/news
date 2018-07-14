package com.wuruoye.news.model.util

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
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

fun Any.blur(context: Context, bitmap: Bitmap): Bitmap {
    var scaleRatio = (bitmap.width / 100).toFloat()
    scaleRatio = if (scaleRatio < 1.0f) 1.0f else scaleRatio
    val width = Math.round(bitmap.width.toDouble() * 1.0 / scaleRatio.toDouble()).toInt()
    val height = Math.round(bitmap.height.toDouble() * 1.0 / scaleRatio.toDouble()).toInt()
    val image = Bitmap.createScaledBitmap(bitmap, width, height, false)
    val rs = RenderScript.create(context)
    val blur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
    val input = Allocation.createFromBitmap(rs, image)
    val output = Allocation.createTyped(rs, input.type)
    blur.setRadius(5.0f)
    blur.setInput(input)
    blur.forEach(output)
    output.copyTo(image)
    input.destroy()
    output.destroy()
    blur.destroy()
    rs.destroy()
    return image
}