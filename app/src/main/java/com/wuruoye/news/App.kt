package com.wuruoye.news

import android.app.Application
import com.wuruoye.library.model.WConfig
import com.wuruoye.library.util.thread.DefaultThreadPool
import com.wuruoye.news.model.NetRequest

/**
 * @Created : wuruoye
 * @Date : 2018/7/9 15:00.
 * @Description : application 配置
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        WConfig.init(this)
        WConfig.initThreadPool(DefaultThreadPool())
        WConfig.initNet(NetRequest())
    }
}