package com.wuruoye.news.ui

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.wuruoye.library.ui.WBaseFragment
import com.wuruoye.news.R
import com.wuruoye.news.contract.LeadContract
import com.wuruoye.news.contract.LeadFragmentContract
import kotlinx.android.synthetic.main.fragment_lead.*

/**
 * @Created : wuruoye
 * @Date : 2018/7/17 10:36.
 * @Description :
 */
class LeadFragment : WBaseFragment<LeadFragmentContract.Presenter>(), LeadFragmentContract.View,
        View.OnClickListener {
    companion object {
        val ITEM_IMG = arrayOf(R.drawable.lead_1, R.drawable.lead_2, R.drawable.lead_3)
        const val DELAY = 3000L
    }
    private var mPage = 0
    private var mNext = false

    override fun getContentView(): Int {
        return R.layout.fragment_lead
    }

    override fun initData(p0: Bundle?) {
        mPage = p0!!.getInt("page")
    }

    override fun initView(p0: View?) {
        iv_lead_bg.setImageResource(ITEM_IMG[mPage])
        tv_lead_next.setOnClickListener(this)

        Handler().postDelayed({
            if (activity != null) {
                if (!mNext) {
                    goToNext()
                }
            }
        }, DELAY)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_lead_next -> {
                goToNext()
            }
        }
    }

    private fun goToNext() {
        mNext = true
        val view = activity as LeadContract.View
        view.goToNext()
    }
}