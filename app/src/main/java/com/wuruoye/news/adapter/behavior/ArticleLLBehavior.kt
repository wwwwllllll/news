package com.wuruoye.news.adapter.behavior

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.View

/**
 * @Created : wuruoye
 * @Date : 2018/7/13 15:04.
 * @Description :
 */
class ArticleLLBehavior : CoordinatorLayout.Behavior<View> {
    constructor(context: Context, attr: AttributeSet)

    override fun layoutDependsOn(parent: CoordinatorLayout?, child: View?,
                                 dependency: View?): Boolean {
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout?, child: View?,
                                        dependency: View?): Boolean {
        val height = dependency!!.height
        val bottom = dependency.bottom
        val progress = bottom * 1F / height

        val x = 0
        val y = parent!!.height - child!!.height * progress

        child.layout(x, y.toInt(), x + child.width, (y + child.height).toInt())
        return true
    }
}