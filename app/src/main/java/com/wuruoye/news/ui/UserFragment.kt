package com.wuruoye.news.ui

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.wuruoye.library.ui.WBaseFragment
import com.wuruoye.news.R
import com.wuruoye.news.contract.UserContract
import com.wuruoye.news.model.Config.HOME_SETTING
import com.wuruoye.news.model.Config.USER_INFO
import com.wuruoye.news.model.Config.USER_LOGIN
import com.wuruoye.news.model.bean.LoginUser
import com.wuruoye.news.model.util.blur
import com.wuruoye.news.model.util.toast
import com.wuruoye.news.presenter.UserPresenter
import com.wuruoye.news.ui.SettingActivity.Companion.RESULT_LOGOUT
import kotlinx.android.synthetic.main.fragment_user.*

/**
 * @Created : wuruoye
 * @Date : 2018/7/9 16:55.
 * @Description : 用户页面
 */
class UserFragment : WBaseFragment<UserContract.Presenter>(), UserContract.View,
        View.OnClickListener {
    companion object {
        val ITEM_ICON = arrayOf(R.drawable.ic_information, R.drawable.ic_collect_not,
                R.drawable.ic_setting)
        val ITEM_TITLE = arrayOf("个人信息", "收藏", "设置")
    }
    private var mIsLogin = false
    private var mLoginUser: LoginUser? = null

    private lateinit var tvUserInfo: TextView

    override fun getContentView(): Int {
        return R.layout.fragment_user
    }

    override fun initData(p0: Bundle?) {
        setPresenter(UserPresenter())
        mIsLogin = mPresenter.isLogin()
    }

    override fun initView(p0: View?) {
        p0!!.post {
            cl_user.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
                override fun onLayoutChange(p0: View?, p1: Int, p2: Int, p3: Int, p4: Int,
                                            p5: Int, p6: Int, p7: Int, p8: Int) {
                    cl_user.dispatchDependentViewsChanged(abl_user)
                }

            })

            civ_user_avatar.setOnClickListener(this)
            tv_user_user.setOnClickListener(this)

            initLL()
            initUser()
        }
    }

    private fun initUser() {
        if (mIsLogin) {
            mPresenter.requestUserInfo()
            tvUserInfo.text = "用户信息"
        }else {
            tvUserInfo.text = "用户登录"
            civ_user_avatar.setImageResource(R.drawable.ic_avatar)
            iv_user_bg.setImageResource(0)
            tv_user_user.text = "未登录"
            tv_user_sign.text = ""
        }
    }

    private fun initLL() {
        val lls = arrayOf(ll_user_info, ll_user_collect,
                ll_user_setting)

        for (i in 0 until lls.size) {
            lls[i].setOnClickListener(this)
            val tv = lls[i].findViewById<TextView>(R.id.tv_item_user)
            val iv = lls[i].findViewById<ImageView>(R.id.iv_item_user)

            tv.text = ITEM_TITLE[i]
            iv.setImageResource(ITEM_ICON[i])

            if (i == 0) {
                tvUserInfo = tv
            }
        }
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.civ_user_avatar -> {

            }
            R.id.tv_user_user -> {

            }
            R.id.ll_user_info -> {
                if (mIsLogin) {
                    val bundle = Bundle()
                    bundle.putParcelable("user", mLoginUser)
                    val intent = Intent(context, UserInfoActivity::class.java)
                    intent.putExtras(bundle)
                    startActivityForResult(intent, USER_INFO)
                }else {
                    startActivityForResult(Intent(context, LoginActivity::class.java), USER_LOGIN)
                }
            }
            R.id.ll_user_collect -> {
                if (mIsLogin) {
                    val bundle = Bundle()
                    bundle.putParcelable("user", mLoginUser)
                    val intent = Intent(context, CollectActivity::class.java)
                    intent.putExtras(bundle)
                    startActivity(intent)
                }else {
                    context!!.toast("请先登录")
                }
            }
            R.id.ll_user_setting -> {
                val intent = Intent(context, SettingActivity::class.java)
                startActivityForResult(intent, HOME_SETTING)
            }
        }
    }

    override fun onLogin() {
        mIsLogin = true
        initUser()
    }

    private fun onLoginResult(loginUser: LoginUser) {
        mIsLogin = true
        tvUserInfo.text = "用户信息"
        onResultUserInfo(loginUser)
    }

    override fun onResultUserInfo(loginUser: LoginUser) {
        mLoginUser = loginUser
        Glide.with(civ_user_avatar)
                .asBitmap()
                .load(loginUser.avatar)
                .listener(object : RequestListener<Bitmap> {
                    override fun onLoadFailed(e: GlideException?, model: Any?,
                                              target: Target<Bitmap>?,
                                              isFirstResource: Boolean): Boolean {
                        civ_user_avatar.setImageResource(R.drawable.ic_avatar)
                        return false
                    }

                    override fun onResourceReady(resource: Bitmap?, model: Any?,
                                                 target: Target<Bitmap>?, dataSource: DataSource?,
                                                 isFirstResource: Boolean): Boolean {
                        civ_user_avatar.setImageBitmap(resource)
                        try {
                            iv_user_bg.setImageBitmap(blur(context!!, resource!!))
                        } catch (e: Exception) {
                        }
                        return true
                    }
                })
                .submit()
        tv_user_user.text = loginUser.name
        tv_user_sign.text = loginUser.sign
        tv_user_user.post {
            cl_user.dispatchDependentViewsChanged(abl_user)
        }
    }

    override fun onResultUserInfo(info: String) {
        context?.toast(info)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == USER_LOGIN && resultCode == RESULT_OK) {
            val loginUser = data!!.getParcelableExtra<LoginUser>("loginUser")
            onLoginResult(loginUser)
        }else if (requestCode == USER_INFO && resultCode == RESULT_OK) {
            val loginUser = data!!.getParcelableExtra<LoginUser>("user")
            onLoginResult(loginUser)
        }else if (requestCode == HOME_SETTING && resultCode == RESULT_OK) {
            val result = data!!.getIntExtra("result", RESULT_LOGOUT)
            if (result == RESULT_LOGOUT) {
                mLoginUser = null
                mIsLogin = false
                initUser()
            }else {
                val loginUser = data.getParcelableExtra<LoginUser>("user")
                onLoginResult(loginUser)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}