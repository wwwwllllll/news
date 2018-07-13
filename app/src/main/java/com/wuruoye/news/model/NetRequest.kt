package com.wuruoye.news.model

import com.wuruoye.library.model.Listener
import com.wuruoye.library.model.WConfig
import com.wuruoye.library.util.FileUtil
import com.wuruoye.library.util.net.IWNet
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * @Created : wuruoye
 * @Date : 2018/7/13 09:42.
 * @Description :
 */
class NetRequest : IWNet {
    private var mType: IWNet.PARAM_TYPE = IWNet.PARAM_TYPE.FORM
    private val cookieList = arrayListOf<Cookie>()

    init {
        mClient = OkHttpClient.Builder()
                .connectTimeout(WConfig.CONNECT_TIME_OUT.toLong(), TimeUnit.SECONDS)
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
                .build()
    }

    override fun setParamType(type: IWNet.PARAM_TYPE) {
        mType = type
    }

    override fun get(url: String, values: Map<String, String>, listener: Listener<String>) {
        request(url, values, listener, IWNet.METHOD.GET)
    }

    override fun post(url: String, values: Map<String, String>, listener: Listener<String>) {
        request(url, values, listener, IWNet.METHOD.POST)
    }

    override fun request(url: String, values: Map<String, String>, listener: Listener<String>,
                         method: IWNet.METHOD) {
        var url = url
        val m = method.name
        try {
            if (method == IWNet.METHOD.GET) {
                url = generateUrl(url, values)
            }
            val builder = Request.Builder().url(url)
            when (method) {
                IWNet.METHOD.GET -> builder.get()
                IWNet.METHOD.HEAD -> builder.head()
                else -> builder.method(m, generateForm(values))
            }
            request(builder.build(), listener)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    override fun downloadFile(url: String, file: String, listener: Listener<String>) {
        val request = Request.Builder()
                .url(url)
                .build()

        mClient.newCall(request)
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        listener.onFail(e.message)
                    }

                    @Throws(IOException::class)
                    override fun onResponse(call: Call, response: Response) {
                        if (response.isSuccessful) {
                            val `is` = response.body()!!.byteStream()
                            val result = FileUtil.writeInputStream(file, `is`)
                            if (result) {
                                listener.onSuccessful(file)
                            } else {
                                listener.onFail("error in write file")
                            }
                        } else {
                            listener.onFail(response.message())
                        }
                    }
                })
    }

    override fun uploadFile(url: String, values: Map<String, String>, files: Map<String, String>, types: List<String>, listener: Listener<String>) {
        if (files.size != types.size) {
            throw IllegalArgumentException()
        }
        val builder = MultipartBody.Builder()
        try {
            builder.addPart(generateForm(values, IWNet.PARAM_TYPE.FORM))
        } catch (ignored: JSONException) {

        }

        var i = 0
        for ((key, value) in files) {
            val file = File(value)
            if (file.isDirectory) {
                listener.onFail("file $value is a directory not file")
                return
            } else if (!file.exists()) {
                listener.onFail("file $value is not exists")
                return
            }
            builder.addFormDataPart(key, file.name, RequestBody
                    .create(MediaType.parse(types[i++]), file))
        }
        val request = Request.Builder()
                .url(url)
                .post(builder.build())
                .build()
        request(request, listener)
    }

    private fun request(request: Request, listener: Listener<String>) {
        try {
            val response = mClient.newCall(request).execute()
            if (response.isSuccessful) {
                listener.onSuccessful(response.body()!!.string())
            } else {
                var message = response.body()?.string()
                if (message == null || message.isEmpty()) {
                    message = response.message().toString()
                }
                listener.onFail(message)
            }
        } catch (e: IOException) {
            listener.onFail(e.message)
        }

    }

    private fun generateUrl(url: String, values: Map<String, String>): String {
        val builder = StringBuilder(url)
        builder.append("?")
        for ((key, value) in values) {
            builder.append(key).append("=").append(value).append("&")
        }

        return builder.toString()
    }

    @Throws(JSONException::class)
    private fun generateForm(values: Map<String, String>, type: IWNet.PARAM_TYPE = mType): RequestBody {
        when (type) {
            IWNet.PARAM_TYPE.FORM -> {
                val builder = FormBody.Builder()
                for ((key, value) in values) {
                    builder.add(key, value)
                }
                return builder.build()
            }
            IWNet.PARAM_TYPE.JSON -> {
                val obj = JSONObject()
                for ((key, value) in values) {
                    obj.put(key, value)
                }
                return RequestBody.create(MediaType.parse("json"), obj.toString())
            }
        }

        throw IllegalArgumentException("unable handler type $type")
    }

    companion object {
        private lateinit var mClient: OkHttpClient
    }
}
