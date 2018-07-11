package com.wuruoye.news.ui

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.wuruoye.library.adapter.FragmentVPAdapter
import com.wuruoye.library.ui.WBaseFragment
import com.wuruoye.news.R
import com.wuruoye.news.contract.HomeContract
import com.wuruoye.news.model.Config.HOME_CHOOSE
import com.wuruoye.news.model.bean.Item
import com.wuruoye.news.presenter.HomePresenter
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * @Created : wuruoye
 * @Date : 2018/7/9 16:53.
 * @Description : 新闻显示页面
 */
class HomeFragment : WBaseFragment<HomeContract.Presenter>(), HomeContract.View,
        View.OnClickListener {
    private lateinit var mCategory: String

    override fun getContentView(): Int {
        return R.layout.fragment_home
    }

    override fun initData(p0: Bundle?) {
        setPresenter(HomePresenter())

        mCategory = mPresenter.getDefaultCategory()
    }

    override fun initView(p0: View?) {
        p0?.post {
            ic_home_more.setOnClickListener(this)

            setCategory(mCategory)
        }
    }

    private fun initVP() {
        val entries = mPresenter.getItems(mCategory)
        val titleList = arrayListOf<String>()
        val itemList = arrayListOf<Item>()
        val categories = arrayListOf<String>()
        for (entry in entries) {
            titleList.add(entry.value.title)
            itemList.add(entry.value)
            categories.add("${mCategory}_${entry.key}")
        }

        val fragments = arrayListOf<Fragment>()
        for (i in 0 until itemList.size) {
            val bundle = Bundle()
            bundle.putParcelable("item", itemList[i])
            bundle.putString("category", categories[i])
            val fragment = ListFragment()
            fragment.arguments = bundle
            fragments.add(fragment)
        }

        val adapter = FragmentVPAdapter(childFragmentManager, titleList, fragments)
        vp_home.adapter = adapter
        tl_home.setupWithViewPager(vp_home)

        vp_home.currentItem = mPresenter.getPosition(entries, mCategory)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == HOME_CHOOSE && resultCode == RESULT_OK) {
            val category = data!!.getStringExtra("category")
            setCategory(category)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun setCategory(category: String) {
        mCategory = category
        initVP()
        tv_home_title.text = mPresenter.getTitle(category)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.ic_home_more -> {
                val intent = Intent(context, ItemChooseActivity::class.java)
                startActivityForResult(intent, HOME_CHOOSE)
            }
        }
    }
}