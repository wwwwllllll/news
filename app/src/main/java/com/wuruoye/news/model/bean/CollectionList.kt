package com.wuruoye.news.model.bean

data class CollectionList (
        var Userid : String,
        var next : String,
        var data : List<ArticleItem>
){}