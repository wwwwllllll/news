package com.wuruoye.news.model.bean

/**
 * @Created : wuruoye
 * @Date : 2018/7/10 09:52.
 * @Description :
 */
data class Title(
        var title: String,
        var items: Map<String, Item>
) {
}