package com.wuruoye.news.model.bean

/**
 * @Created : wuruoye
 * @Date : 2018/7/14 19:50.
 * @Description :
 */
data class ArticleCollect(
        var id: Int,
        var article: String,
        var user: String,
        var content: ArticleItem,
        var time: Long
)