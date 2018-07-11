package com.wuruoye.news.presenter

import com.wuruoye.news.contract.HomeContract
import com.wuruoye.news.model.UserCache
import com.wuruoye.news.model.bean.Item

/**
 * @Created : wuruoye
 * @Date : 2018/7/10 19:30.
 * @Description :
 */
class HomePresenter : HomeContract.Presenter() {
    private val userCache = UserCache.getInstance()

    override fun getDefaultCategory(): String {
        return "news_news_index"
    }

    override fun getItems(category: String): Map<String, Item> {
        val categories = category.split("_")
        return userCache.api[categories[0]]!!.api[categories[1]]!!.items
    }
}