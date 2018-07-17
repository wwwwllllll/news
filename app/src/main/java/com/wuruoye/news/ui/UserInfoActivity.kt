package com.wuruoye.news.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.bumptech.glide.Glide
import com.wuruoye.library.ui.WBaseActivity
import com.wuruoye.library.util.media.IWPhoto
import com.wuruoye.library.util.media.WPhoto
import com.wuruoye.news.R
import com.wuruoye.news.contract.UserInfoContract
import com.wuruoye.news.model.bean.LoginUser
import com.wuruoye.news.model.util.toast
import com.wuruoye.news.presenter.UserInfoPresenter
import kotlinx.android.synthetic.main.activity_user_info.*

/**
 * @Created : wuruoye
 * @Date : 2018/7/14 14:33.
 * @Description :
 */
class UserInfoActivity : WBaseActivity<UserInfoContract.Presenter>(), UserInfoContract.View,
        View.OnClickListener, IWPhoto.OnWPhotoListener<String> {
    companion object {
        const val TYPE_NAME = 1
        const val TYPE_SIGN = 2
        const val TYPE_EMAIL = 3
        const val TYPE_PHONE = 4
        val ITEM_PHOTO = arrayOf("相册", "拍照")
    }
    private lateinit var mLoginUser: LoginUser
    private lateinit var mPhotoGet: WPhoto
    private var mCurrentType = 0

    private lateinit var dlgPhoto: AlertDialog
    private lateinit var dlgLoading: AlertDialog
    private lateinit var dlgUserInfo: AlertDialog
    private lateinit var tilUserInfo: TextInputLayout
    private lateinit var etUserInfo: EditText
    private lateinit var btnPublish: Button

    override fun getContentView(): Int {
        return R.layout.activity_user_info
    }

    override fun initData(p0: Bundle?) {
        mLoginUser = p0!!.getParcelable("user")

        setPresenter(UserInfoPresenter())
        mPhotoGet = WPhoto(this)
    }

    override fun initView() {
        initDlg()
        setUserInfo()

        iv_user_info_back.setOnClickListener(this)
        ll_user_info_name.setOnClickListener(this)
        ll_user_info_sign.setOnClickListener(this)
        ll_user_info_email.setOnClickListener(this)
        ll_user_info_phone.setOnClickListener(this)
        ll_user_info_avatar.setOnClickListener(this)
    }

    @SuppressLint("InflateParams")
    private fun initDlg() {
        val view = LayoutInflater.from(this)
                .inflate(R.layout.dlg_user_info, null)
        tilUserInfo = view.findViewById(R.id.til_dlg_user_info)
        etUserInfo = view.findViewById(R.id.et_dlg_user_info)

        dlgUserInfo = AlertDialog.Builder(this, R.style.DlgTheme)
                .setView(view)
                .setPositiveButton("修改", null)
                .setNegativeButton("取消", null)
                .create()

        val loadingView = LayoutInflater.from(this)
                .inflate(R.layout.dlg_loading, null)
        loadingView.findViewById<TextView>(R.id.tv_dlg_loading).text = "上传图片中..."
        dlgLoading = AlertDialog.Builder(this, R.style.DlgTheme)
                .setView(loadingView)
                .create()

        dlgPhoto = AlertDialog.Builder(this, R.style.DlgTheme)
                .setTitle("选择方式")
                .setItems(ITEM_PHOTO) {_, position ->
                    when (position) {
                        0 -> {
                            mPhotoGet.choosePhoto(mPresenter.getAvatarPath(), 1, 1,
                                    500, 500, this)
                        }
                        1 -> {
                            mPhotoGet.takePhoto(mPresenter.getAvatarPath(), 1, 1,
                                    500, 500, this)
                        }
                    }
                }
                .create()
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.iv_user_info_back -> {
                onBackPressed()
            }
            R.id.ll_user_info_avatar -> {
                dlgPhoto.show()
            }
            R.id.ll_user_info_name -> {
                showDlg(TYPE_NAME)
            }
            R.id.ll_user_info_sign -> {
                showDlg(TYPE_SIGN)
            }
            R.id.ll_user_info_email -> {
                showDlg(TYPE_EMAIL)
            }
            R.id.ll_user_info_phone -> {
                showDlg(TYPE_PHONE)
            }
            btnPublish.id -> {
                onDlg()
            }
        }
    }

    private fun setUserInfo() {
        Glide.with(civ_user_info)
                .load(mLoginUser.avatar)
                .into(civ_user_info)
        tv_user_info_id.text = mLoginUser.id
        tv_user_info_name.text = mLoginUser.name
        tv_user_info_sign.text = mLoginUser.sign
        tv_user_info_email.text = mLoginUser.email
        tv_user_info_phone.text = mLoginUser.phone
    }

    private fun showDlg(type: Int) {
        mCurrentType = type
        when (type) {
            TYPE_NAME -> {
                dlgUserInfo.setTitle("修改昵称")
                tilUserInfo.isCounterEnabled = true
                tilUserInfo.counterMaxLength = 16
                etUserInfo.maxLines = 1
                etUserInfo.setSingleLine()
                etUserInfo.imeOptions = EditorInfo.IME_ACTION_DONE
                tilUserInfo.hint = mLoginUser.name
            }
            TYPE_SIGN -> {
                dlgUserInfo.setTitle("修改签名")
                tilUserInfo.isCounterEnabled = true
                tilUserInfo.counterMaxLength  = 100
                etUserInfo.maxLines = 5
                etUserInfo.setSingleLine(false)
                etUserInfo.imeOptions = EditorInfo.IME_NULL
                tilUserInfo.hint = mLoginUser.sign
            }
            TYPE_EMAIL -> {
                dlgUserInfo.setTitle("修改邮箱")
                tilUserInfo.isCounterEnabled = false
                etUserInfo.maxLines = 1
                etUserInfo.setSingleLine()
                etUserInfo.imeOptions = EditorInfo.IME_ACTION_DONE
                tilUserInfo.hint = mLoginUser.email
            }
            TYPE_PHONE -> {
                dlgUserInfo.setTitle("修改手机")
                tilUserInfo.isCounterEnabled = false
                etUserInfo.maxLines = 1
                etUserInfo.setSingleLine()
                etUserInfo.imeOptions = EditorInfo.IME_ACTION_DONE
                tilUserInfo.hint = mLoginUser.phone
            }
        }
        dlgUserInfo.show()
        btnPublish = dlgUserInfo.getButton(AlertDialog.BUTTON_POSITIVE)
        btnPublish.setOnClickListener(this)
    }

    private fun onDlg() {
        val user = LoginUser(mLoginUser)
        when (mCurrentType) {
            TYPE_NAME -> {
                val name = etUserInfo.text.toString()
                if (name.length < 2 || name.length > 10) {
                    tilUserInfo.error = "用户名长度应在 2-10 之间"
                    return
                }
                user.name = name
            }
            TYPE_SIGN -> {
                val sign = etUserInfo.text.toString().trim()
                if (sign.isEmpty() || sign.length > 100) {
                    tilUserInfo.error = "用户签名长度应在 1-100 之间"
                    return
                }
                user.sign = sign
            }
            TYPE_PHONE -> {
                val phone = etUserInfo.text.toString()
                if (!mPresenter.checkPhone(phone)) {
                    tilUserInfo.error = "请输入正确手机号"
                    return
                }
                user.phone = phone
            }
            TYPE_EMAIL -> {
                val email = etUserInfo.text.toString()
                if (!mPresenter.checkEmail(email)) {
                    tilUserInfo.error = "请输入正确邮箱地址"
                    return
                }
                user.email = email
            }
        }
        etUserInfo.text.clear()
        mPresenter.requestUserInfo(user)
        dlgUserInfo.dismiss()
    }

    override fun onPhotoResult(p0: String?) {
        dlgLoading.show()
        mPresenter.requestUpload(p0!!)
    }

    override fun onPhotoError(p0: String?) {
        toast(p0!!)
    }

    override fun onResultAvatar(result: Boolean, info: String) {
        dlgLoading.dismiss()
        if (result) {
            mLoginUser.avatar = mPresenter.getAvatarPath(info)
            setUserInfo()
        }else {
            toast(info)
        }
    }

    override fun onResultUserInfo(info: String) {
        dlgLoading.dismiss()
        toast(info)
    }

    override fun onResultUserInfo(info: LoginUser) {
        dlgLoading.dismiss()
        mLoginUser = info
        setUserInfo()
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("user", mLoginUser)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}