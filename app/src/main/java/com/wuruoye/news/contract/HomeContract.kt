package com.wuruoye.news.contract

import com.wuruoye.library.contract.WIView
import com.wuruoye.library.contract.WPresenter
import com.wuruoye.news.model.bean.Item

/**
 * @Created : wuruoye
 * @Date : 2018/7/9 16:53.
 * @Description :
 */
class HomeContract {
    interface View : WIView {

    }

    abstract class Presenter : WPresenter<View>() {
        abstract fun getDefaultCategory(): String
        abstract fun getItems(category: String): List<Map.Entry<String, Item>>
        abstract fun getPosition(entries: List<Map.Entry<String, Item>>, category: String): Int
        abstract fun getTitle(category: String): String
    }
}