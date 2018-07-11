package com.wuruoye.news.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.wuruoye.library.adapter.WBaseRVAdapter
import com.wuruoye.news.R
import com.wuruoye.news.model.bean.Item
import kotlinx.android.synthetic.main.item_item.view.*

/**
 * @Created : wuruoye
 * @Date : 2018/7/11 12:05.
 * @Description :
 */
class ItemRVAdapter : WBaseRVAdapter<Item>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getData(position)
        val viewHolder = holder as ViewHolder
        with(viewHolder) {
            tv.text = item.title
            itemView.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tv = itemView.findViewById<TextView>(R.id.tv_item_item)!!
    }
}