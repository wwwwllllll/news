package com.wuruoye.news.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.CompoundButton
import android.widget.RadioGroup
import com.wuruoye.library.ui.WBaseActivity
import com.wuruoye.news.R
import com.wuruoye.news.contract.SettingContract
import com.wuruoye.news.model.Config.SETTING_LOGIN
import com.wuruoye.news.model.bean.LoginUser
import com.wuruoye.news.model.util.toast
import com.wuruoye.news.presenter.SettingPresenter
import kotlinx.android.synthetic.main.activity_setting.*

/**
 * @Created : wuruoye
 * @Date : 2018/7/15 12:46.
 * @Description :
 */
class SettingActivity : WBaseActivity<SettingContract.Presenter>(), SettingContract.View,
        View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    companion object {
        const val RESULT_LOGIN = 1
        const val RESULT_LOGOUT = 2
        val ITEM_TEXT = arrayOf(R.id.rb_setting_text_smallest, R.id.rb_setting_text_small,
                R.id.rb_setting_text_normal, R.id.rb_setting_text_large,
                R.id.rb_setting_text_largest)
        val ITEM_TEXT_SIZE = arrayOf(0.6F, 0.8F, 1F, 1.2F, 1.4F)
        val ITEM_TEXT_TITLE = arrayOf("最小", "较小", "正常", "较大", "最大")
    }
    private var mIsLogin = false
    private var mLoginChanged = false
    private var mLoginUser: LoginUser? = null

    private lateinit var dlgText: AlertDialog
    private lateinit var rgText: RadioGroup

    override fun getContentView(): Int {
        return R.layout.activity_setting
    }

    override fun initData(p0: Bundle?) {
        setPresenter(SettingPresenter())
        mIsLogin = mPresenter.isLogin()
    }

    override fun initView() {
        initDlg()

        iv_setting_back.setOnClickListener(this)
        btn_setting_login.setOnClickListener(this)
        tv_setting_reload_api.setOnClickListener(this)
        ll_setting_img.setOnClickListener(this)
        ll_setting_proxy.setOnClickListener(this)
        ll_setting_web.setOnClickListener(this)
        ll_setting_text.setOnClickListener(this)
        s_setting_img.setOnCheckedChangeListener(this)
        s_setting_proxy.setOnCheckedChangeListener(this)
        s_setting_web.setOnCheckedChangeListener(this)

        s_setting_img.isChecked = mPresenter.getNoImg()
        s_setting_proxy.isChecked = mPresenter.getProxy()
        s_setting_web.isChecked = mPresenter.getWeb()
        try {
            tv_setting_text.text = ITEM_TEXT_TITLE[ITEM_TEXT_SIZE
                    .indexOf(mPresenter.getTextSize())]
        } catch (e: Exception) {
            mPresenter.setTextSize(ITEM_TEXT_SIZE[2])
            tv_setting_text.text = ITEM_TEXT_TITLE[2]
        }
        if (mIsLogin) {
            btn_setting_login.text = "用户登出"
        }else {
            btn_setting_login.text = "用户登录"
        }
    }

    @SuppressLint("InflateParams")
    private fun initDlg() {
        rgText = LayoutInflater.from(this)
                .inflate(R.layout.dlg_text_picker, null) as RadioGroup
        dlgText = AlertDialog.Builder(this, R.style.DlgTheme)
                .setTitle("选择字体大小")
                .setView(rgText)
                .setPositiveButton("确定") { _, _ ->
                    val select = ITEM_TEXT.indexOf(rgText.checkedRadioButtonId)
                    val size = ITEM_TEXT_SIZE[select]
                    mPresenter.setTextSize(size)
                    tv_setting_text.text = ITEM_TEXT_TITLE[select]
                    toast("重新进入应用查看更改")
                }
                .setNegativeButton("取消", null)
                .create()
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.iv_setting_back -> {
                onBackPressed()
            }
            R.id.btn_setting_login -> {
                if (mIsLogin) {
                    mPresenter.requestLogout()
                }else {
                    startActivityForResult(Intent(this, LoginActivity::class.java),
                            SETTING_LOGIN)
                }
            }
            R.id.tv_setting_reload_api -> {
                mPresenter.requestReloadApi()
            }
            R.id.ll_setting_img -> {
                s_setting_img.isChecked = !s_setting_img.isChecked
            }
            R.id.ll_setting_proxy -> {
                s_setting_proxy.isChecked = !s_setting_proxy.isChecked
            }
            R.id.ll_setting_web -> {
                s_setting_web.isChecked = !s_setting_web.isChecked
            }
            R.id.ll_setting_text -> {
                rgText.check(ITEM_TEXT[ITEM_TEXT_SIZE.indexOf(mPresenter.getTextSize())])
                dlgText.show()
            }
        }
    }

    override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
        when (p0!!.id) {
            R.id.s_setting_img -> {
                mPresenter.setNoImg(p1)
            }
            R.id.s_setting_proxy -> {
                mPresenter.setProxy(p1)
            }
            R.id.s_setting_web -> {
                mPresenter.setWeb(p1)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SETTING_LOGIN && resultCode == Activity.RESULT_OK) {
            mLoginChanged = true
            mLoginUser = data!!.getParcelableExtra("loginUser")
            btn_setting_login.text = "用户登出"
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onBackPressed() {
        if (mLoginChanged) {
            val intent = Intent()
            if (mLoginUser == null) {
                intent.putExtra("result", RESULT_LOGOUT)
            }else {
                intent.putExtra("result", RESULT_LOGIN)
                intent.putExtra("user", mLoginUser)
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }else {
            super.onBackPressed()
        }
    }

    override fun onResultLogout(result: Boolean, info: String) {
        if (result) {
            mIsLogin = false
            mLoginChanged = true
            mLoginUser = null
            btn_setting_login.text = "用户登录"
        }else {
            toast(info)
        }
    }

    override fun onResultReloadApi(result: Boolean, info: String) {
        toast(info)
    }
}