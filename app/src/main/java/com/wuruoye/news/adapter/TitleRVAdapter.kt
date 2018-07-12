package com.wuruoye.news.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.wuruoye.library.adapter.WBaseRVAdapter
import com.wuruoye.library.widget.SmartLinearLayout
import com.wuruoye.news.R
import com.wuruoye.news.model.bean.Item
import com.wuruoye.news.model.bean.Title

/**
 * @Created : wuruoye
 * @Date : 2018/7/11 12:00.
 * @Description :
 */
class TitleRVAdapter : WBaseRVAdapter<Map.Entry<String, Title>>() {
    private var mOnActionListener: OnActionListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_title, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val titleEntry = getData(position)
        val viewHolder = holder as ViewHolder
        with(viewHolder) {
            tv.text = titleEntry.value.title
            sll.removeAllViews()

            for (entry in titleEntry.value.items) {
                val textView = LayoutInflater.from(itemView.context)
                        .inflate(R.layout.item_item, sll, false) as TextView
                textView.text = entry.value.title
                sll.addView(textView)
                textView.setOnClickListener {
                    mOnActionListener?.onItemClick("${titleEntry.key}_${entry.key}")
                }
            }

            tv.setOnClickListener {
                mOnActionListener?.onItemClick(titleEntry.key)
            }

        }
    }

    private fun parseItems(map: Map<String, Item>): List<Item> {
        val list = arrayListOf<Item>()
        for (entry in map.entries) {
            list.add(entry.value)
        }
        return list
    }

    fun setOnActionListener(listener: OnActionListener) {
        mOnActionListener = listener
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tv = itemView.findViewById<TextView>(R.id.tv_item_title)!!
        val sll = itemView.findViewById<SmartLinearLayout>(R.id.sll_item_title)!!
    }

    interface OnActionListener {
        fun onItemClick(category: String)
    }
}