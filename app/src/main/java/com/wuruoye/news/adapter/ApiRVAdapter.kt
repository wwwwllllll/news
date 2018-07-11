package com.wuruoye.news.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.wuruoye.library.adapter.WBaseRVAdapter
import com.wuruoye.news.R
import com.wuruoye.news.model.bean.Api
import kotlinx.android.synthetic.main.item_api.view.*

/**
 * @Created : wuruoye
 * @Date : 2018/7/11 14:25.
 * @Description :
 */
class ApiRVAdapter: WBaseRVAdapter<Map.Entry<String, Api>>() {
    private var mOnActionListener: OnActionListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_api, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val apiEntry = getData(position)
        val viewHolder = holder as ViewHolder
        with(viewHolder) {
            tv.text = apiEntry.value.title

            val adapter = TitleRVAdapter()
            adapter.data = ArrayList(apiEntry.value.api.entries)
            adapter.setOnActionListener(object : TitleRVAdapter.OnActionListener {
                override fun onItemClick(category: String) {
                    mOnActionListener?.onItemClick("${apiEntry.key}_$category")
                }
            })
            rv.adapter = adapter
            rv.layoutManager = LinearLayoutManager(itemView.context)
        }
    }

    fun setOnActionListener(listener: OnActionListener) {
        mOnActionListener = listener
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv = itemView.findViewById<TextView>(R.id.tv_item_api)!!
        val rv = itemView.findViewById<RecyclerView>(R.id.rv_item_api)!!
    }

    interface OnActionListener {
        fun onItemClick(category: String)
    }
}