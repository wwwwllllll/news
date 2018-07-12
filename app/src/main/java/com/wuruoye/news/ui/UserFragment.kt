package com.wuruoye.news.ui

import android.os.Bundle
import android.view.View
import com.wuruoye.library.ui.WBaseFragment
import com.wuruoye.news.R
import com.wuruoye.news.contract.UserContract
import kotlinx.android.synthetic.main.fragment_user.*

/**
 * @Created : wuruoye
 * @Date : 2018/7/9 16:55.
 * @Description : 用户页面
 */
class UserFragment : WBaseFragment<UserContract.Presenter>(), UserContract.View {
    override fun getContentView(): Int {
        return R.layout.fragment_user
    }

    override fun initData(p0: Bundle?) {

    }

    override fun initView(p0: View?) {
        p0!!.post {
            abl_user.post {
                cl_user.dispatchDependentViewsChanged(abl_user)
            }
        }
    }
}