package com.wuruoye.news.model.util

import com.google.gson.Gson
import com.wuruoye.news.model.bean.*
import org.json.JSONArray
import org.json.JSONObject

/**
 * @Created : wuruoye
 * @Date : 2018/7/10 10:36.
 * @Description : 网络数据解析类
 */
object DataUtil {
    private val sGson = Gson()

    fun parseApi(info: String): Map<String, Api> {
        val apiMap = LinkedHashMap<String, Api>()

        val appArr = JSONArray(info)
        for (l in 0 until appArr.length()) {
            val appObj = appArr.getJSONObject(l)
            if (appObj.getString("name") == "sina") {
                val apiArr = appObj.getJSONArray("api")

                for (i in 0 until apiArr.length()) {
                    val apiObj = apiArr.getJSONObject(i)
                    val apiName = apiObj.getString("name")
                    val apiTitle = apiObj.getString("title")
                    val titleArr = apiObj.getJSONArray("titles")

                    val titleMap = LinkedHashMap<String, Title>()
                    for (j in 0 until titleArr.length()) {
                        val titleObj = titleArr.getJSONObject(j)
                        val titleName = titleObj.getString("name")
                        val titleTitle = titleObj.getString("title")
                        val itemArr = titleObj.getJSONArray("items")

                        val itemMap = LinkedHashMap<String, Item>()
                        for (k in 0 until itemArr.length()) {
                            val itemObj = itemArr.getJSONObject(k)
                            val itemName = itemObj.getString("name")
                            val itemTitle = itemObj.getString("title")
                            val url = itemObj.getString("url")

                            itemMap[itemName] = Item(itemTitle, url)
                        }
                        titleMap[titleName] = Title(titleTitle, itemMap)
                    }
                    apiMap[apiName] = Api(apiTitle, titleMap)
                }
            }
        }

        return apiMap
    }

    fun parseArticleList(info: String): ArticleList {
        return sGson.fromJson(info, ArticleList::class.java)
    }

    fun parseArticleDetail(info: String): ArticleDetail {
        return sGson.fromJson(info, ArticleDetail::class.java)
    }

    fun parseLoginUser(info: String): LoginUser {
        return sGson.fromJson(info, LoginUser::class.java)
    }

    fun parseResult(info: String): NetResult {
        val obj = JSONObject(info)
        val result = obj.getBoolean("result")
        val s = obj.getString("info")
        return NetResult(result, s)
    }

    fun parseArticleComment(info: String): ArticleComment {
        return sGson.fromJson(info, ArticleComment::class.java)
    }

    fun parseArticleCommentList(info: String): ArticleCommentList {
        return sGson.fromJson(info, ArticleCommentList::class.java)
    }

    fun parseArticleInfo(info: String): ArticleInfo {
        return sGson.fromJson(info, ArticleInfo::class.java)
    }

    fun parseArticleCollectList(info: String): ArticleCollectList {
        val obj = JSONObject(info)
        val list = obj.getJSONArray("list")
        val next = obj.getLong("next")
        val collectList = arrayListOf<ArticleCollect>()
        for (i in 0 until list.length()) {
            val collectObj = list.getJSONObject(i)
            val article = collectObj.getString("article")
            val id = collectObj.getInt("id")
            val user = collectObj.getString("user")
            val time = collectObj.getLong("time")
            val content = collectObj.getString("content")
            collectList.add(ArticleCollect(id, article, user,
                    sGson.fromJson(content, ArticleItem::class.java), time))
        }
        return ArticleCollectList(next, collectList)
    }
}