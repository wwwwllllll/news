package com.wuruoye.news.model.bean

/**
 * @Created : wuruoye
 * @Date : 2018/7/10 09:47.
 * @Description : 新闻全部 Api 类
 */
data class Api(
        var title: String,
        var api: Map<String, Title>
)