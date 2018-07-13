package com.wuruoye.news.contract

import com.wuruoye.library.contract.WIView
import com.wuruoye.library.contract.WPresenter
import com.wuruoye.news.model.bean.ArticleList
import com.wuruoye.news.model.bean.CollectionList

class CollectionContract {
    interface View : WIView {
        fun GetCollectionList(collectionList: CollectionList) {

        }
    }

    abstract class Presenter: WPresenter<View>() {
        abstract fun GetList(userid:String,url:String,page:String)
    }
}