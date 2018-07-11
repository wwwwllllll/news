package com.wuruoye.news.model.bean

/**
 * @Created : wuruoye
 * @Date : 2018/7/10 19:47.
 * @Description :
 */
data class ArticleList(
        var app: String,
        var category: String,
        var next: String,
        var data: List<ArticleItem>
)