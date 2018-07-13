package com.wuruoye.news.presenter

import android.net.sip.SipSession
import com.wuruoye.library.model.Listener
import com.wuruoye.library.util.net.WNet
import com.wuruoye.news.contract.CollectionContract
import com.wuruoye.news.model.bean.ArticleList
import com.wuruoye.news.model.util.DataUtil
import java.net.URL

class CollectionPresenter : CollectionContract.Presenter() {

    override fun GetList(userid: String, url: String, page:String) {

        var value = mapOf(Pair("UserId",userid), Pair("Page",page))

        val url = url.replace("\${page}", page)

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        WNet.getInBackground(url, value, object: Listener<String> {

            override fun onSuccessful(p0: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                val list = DataUtil.parseColllectionList(p0!!)
                view.GetCollectionList(list)
            }

            override fun onFail(p0: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

}