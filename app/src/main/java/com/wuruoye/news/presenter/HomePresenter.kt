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
        return "news_news"
    }

    override fun getItems(category: String): List<Map.Entry<String, Item>> {
        val categories = category.split("_")
        val map = userCache.api[categories[0]]!!.api[categories[1]]!!.items
        return ArrayList(map.entries)
    }

    override fun getPosition(entries: List<Map.Entry<String, Item>>, category: String): Int {
        val categories = category.split("_")
        if (categories.size == 3) {
            for (i in 0 until entries.size) {
                if (entries[i].key == categories[2]) {
                    return i
                }
            }
        }
        return 0
    }

    override fun getTitle(category: String): String {
        val api = userCache.api
        val categories = category.split("_")
        return api[categories[0]]!!.api[categories[1]]!!.title
    }
}