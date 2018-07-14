package com.wuruoye.news.presenter

import com.wuruoye.library.model.Listener
import com.wuruoye.library.util.net.WNet
import com.wuruoye.news.contract.CollectionContract

class CollectionPresenter : CollectionContract.Presenter() {

    override fun GetList(userid: String, url: String, page:String) {

        var value = mapOf(Pair("UserId",userid), Pair("Page",page))

        val url = url.replace("\${page}", page)

        WNet.getInBackground(url, value, object: Listener<String> {

            override fun onSuccessful(p0: String?) {
//                val list = DataUtil.parseColllectionList(p0!!)
//                view.GetCollectionList(list)
            }

            override fun onFail(p0: String?) {
            }
        })
    }

}