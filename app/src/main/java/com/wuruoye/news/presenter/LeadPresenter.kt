package com.wuruoye.news.presenter

import com.wuruoye.news.contract.LeadContract
import com.wuruoye.news.model.UserCache

/**
 * @Created : wuruoye
 * @Date : 2018/7/17 10:46.
 * @Description :
 */
class LeadPresenter : LeadContract.Presenter() {
    private val mUserCache = UserCache.getInstance()

    override fun setLead() {
        mUserCache.isLead = true
    }
}