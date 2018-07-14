package com.wuruoye.news.model.bean

/**
 * @Created : wuruoye
 * @Date : 2018/7/14 09:04.
 * @Description :
 */
data class ArticleCommentList(
        var next: Long,
        var list: List<ArticleComment>
)