package com.wuruoye.news.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.WindowManager
import com.wuruoye.library.adapter.FragmentVPAdapter
import com.wuruoye.library.ui.WBaseActivity
import com.wuruoye.news.R
import com.wuruoye.news.contract.LeadContract
import com.wuruoye.news.presenter.LeadPresenter
import kotlinx.android.synthetic.main.activity_lead.*

/**
 * @Created : wuruoye
 * @Date : 2018/7/17 10:18.
 * @Description :
 */
class LeadActivity : WBaseActivity<LeadContract.Presenter>(), LeadContract.View{
    companion object {
        const val MAX_PAGE = 3
    }
    override fun getContentView(): Int {
        return R.layout.activity_lead
    }

    override fun initData(p0: Bundle?) {
        setPresenter(LeadPresenter())
    }

    override fun initView() {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        initVP()
    }

    private fun initVP() {
        val fragments = arrayListOf<Fragment>()
        for (i in 0 until MAX_PAGE) {
            val bundle = Bundle()
            bundle.putInt("page", i)
            val fragment = LeadFragment()
            fragment.arguments = bundle
            fragments.add(fragment)
        }

        val adapter = FragmentVPAdapter(supportFragmentManager, arrayListOf(), fragments)
        vp_lead.adapter = adapter
    }

    override fun goToNext() {
        val current = vp_lead.currentItem
        if (current < MAX_PAGE - 1) {
            vp_lead.currentItem = current + 1
        }else {
            mPresenter.setLead()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}