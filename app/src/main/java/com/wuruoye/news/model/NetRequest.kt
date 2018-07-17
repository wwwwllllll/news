package com.wuruoye.news.model

import com.wuruoye.library.model.WConfig
import com.wuruoye.library.util.net.OKHttpNet
import com.wuruoye.library.util.net.WNetException
import okhttp3.*
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * @Created : wuruoye
 * @Date : 2018/7/17 12:05.
 * @Description :
 */
class NetRequest : OKHttpNet() {
    private val cookieList = arrayListOf<Cookie>()
    init {
        setClient(getClient().newBuilder()
                .connectTimeout(WConfig.CONNECT_TIME_OUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(10000, TimeUnit.SECONDS)
                .cookieJar(object : CookieJar {
                    override fun saveFromResponse(url: HttpUrl?, cookies: MutableList<Cookie>?) {
                        for (cookie in cookies!!) {
                            cookieList.add(cookie)
                        }
                    }

                    override fun loadForRequest(url: HttpUrl?): MutableList<Cookie> {
                        return cookieList
                    }

                })
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                            .cacheControl(CacheControl.FORCE_NETWORK)
                            .addHeader("Cookie", "statuid=__10.13.240.62_1531099946_0.76404300; statuidsrc=Paw%2F3.1.5+%28Macintosh%3B+OS+X%2F10.14.0%29+GCDHTTPRequest%6010.13.240.62%60http%3A%2F%2Finterface.sina.cn%2Fwap_api%2Flayout_col.d.json%3Fshowid%3D56261%60%60__10.13.240.62_1531099946_0.76404300; ustat=__10.13.240.62_1531099946_0.76404300; genTime=1531099946; TUIJIAN=usrmdinst_5; vt=4; TUIJIAN_1=usrmdinst_2")
                            .build()
                    chain.proceed(request)
                }
                .build())
    }

    override fun uploadFile(url: String, values: MutableMap<String, String>,
                            files: MutableMap<String, String>, types: MutableList<String>): String {
        if (files.size != types.size) {
            throw IllegalArgumentException()
        }
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        for ((key, value) in values) {
            builder.addFormDataPart(key, value)
        }

        var i = 0
        for ((key, value) in files) {
            val file = File(value)
            if (file.isDirectory) {
                throw WNetException("file $value is a directory not file")
            } else if (!file.exists()) {
                throw WNetException("file $value is not exists")
            }
            builder.addFormDataPart(key, file.name, RequestBody
                    .create(MediaType.parse(types[i++]), file))
        }
        val request = Request.Builder()
                .url(url)
                .post(builder.build())
                .build()
        try {
            val response = getClient().newCall(request).execute()
            return if (response.isSuccessful) {
                response.body()!!.string()
            } else {
                throw WNetException(response.message())
            }
        } catch (var3: IOException) {
            throw WNetException(var3.message)
        }

    }
}