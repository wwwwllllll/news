package com.wuruoye.news.model.bean

/**
 * @Created : wuruoye
 * @Date : 2018/7/12 08:25.
 * @Description :
 */
data class ArticleDetail(
        var app: String,
        var category: String,
        var id: String,
        var data: List<DetailItem>
)