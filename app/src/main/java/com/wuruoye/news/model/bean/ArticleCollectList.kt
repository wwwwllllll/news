package com.wuruoye.news.model.bean

/**
 * @Created : wuruoye
 * @Date : 2018/7/14 20:48.
 * @Description :
 */
data class ArticleCollectList(
        var next: Long,
        var list: List<ArticleCollect>
)