package com.wuruoye.news.model.bean

/**
 * @Created : wuruoye
 * @Date : 2018/7/14 09:57.
 * @Description :
 */
data class ArticleInfo (
        var comment_num: Int,
        var praise_num: Int,
        var collect_num: Int,
        var is_praise: Boolean,
        var is_collect: Boolean
) {
}