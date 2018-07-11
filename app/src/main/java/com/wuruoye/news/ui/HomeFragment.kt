package com.wuruoye.news.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.wuruoye.library.adapter.FragmentVPAdapter
import com.wuruoye.library.ui.WBaseFragment
import com.wuruoye.news.R
import com.wuruoye.news.contract.HomeContract
import com.wuruoye.news.model.bean.Item
import com.wuruoye.news.presenter.HomePresenter
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * @Created : wuruoye
 * @Date : 2018/7/9 16:53.
 * @Description : 新闻显示页面
 */
class HomeFragment : WBaseFragment<HomeContract.Presenter>(), HomeContract.View {
    private lateinit var mCategory: String

    override fun getContentView(): Int {
        return R.layout.fragment_home
    }

    override fun initData(p0: Bundle?) {
        setPresenter(HomePresenter())

        mCategory = mPresenter.getDefaultCategory()
    }

    override fun initView(p0: View?) {
        val map = mPresenter.getItems(mCategory)
        val titleList = arrayListOf<String>()
        val itemList = arrayListOf<Item>()
        val categories = arrayListOf<String>()
        for (entry in map.entries) {
            titleList.add(entry.value.title)
            itemList.add(entry.value)
            categories.add(mCategory + entry.key)
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

        p0!!.post {
            val adapter = FragmentVPAdapter(childFragmentManager, titleList, fragments)
            vp_home.adapter = adapter
            tl_home.setupWithViewPager(vp_home)
        }
    }
}