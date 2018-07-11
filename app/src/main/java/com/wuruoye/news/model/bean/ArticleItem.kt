package com.wuruoye.news.model.bean

/**
 * @Created : wuruoye
 * @Date : 2018/7/10 19:10.
 * @Description :
 */
data class ArticleItem(
        var id: String,
        var title: String,
        var forward: String,
        var author: String,
        var millis: Long,
        var bg_img: String,
        var url: String,
        var type: String,
        var open: String
)