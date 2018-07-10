package com.wuruoye.news.model.util

import android.support.v4.util.ArrayMap
import com.wuruoye.news.model.bean.Api
import com.wuruoye.news.model.bean.Item
import com.wuruoye.news.model.bean.Title
import org.json.JSONArray

/**
 * @Created : wuruoye
 * @Date : 2018/7/10 10:36.
 * @Description : 网络数据解析类
 */
object DataUtil {
    fun parseApi(info: String): ArrayMap<String, Api> {
        val apiMap = ArrayMap<String, Api>()

        val apiArr = JSONArray(info)
        for (i in 0 until apiArr.length()) {
            val apiObj = apiArr.getJSONObject(i)
            val apiName = apiObj.getString("name")
            val apiTitle = apiObj.getString("title")
            val titleArr = apiObj.getJSONArray("titles")

            val titleMap = ArrayMap<String, Title>()
            for (j in 0 until titleArr.length()) {
                val titleObj = titleArr.getJSONObject(j)
                val titleName = titleObj.getString("name")
                val titleTitle = titleObj.getString("title")
                val itemArr = titleObj.getJSONArray("items")

                val itemMap = ArrayMap<String, Item>()
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

        return apiMap
    }
}