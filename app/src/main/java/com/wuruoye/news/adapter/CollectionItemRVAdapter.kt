package com.wuruoye.news.adapter

import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.wuruoye.library.adapter.WBaseRVAdapter
import com.wuruoye.news.R
import com.wuruoye.news.model.bean.ArticleItem
import com.wuruoye.news.model.util.DataUtil
import kotlinx.android.synthetic.main.item_collectnews.view.*
import java.util.concurrent.TimeoutException

class CollectionItemRVAdapter: WBaseRVAdapter<ArticleItem>() {

    companion object {
        const val TYPE_NORMAL = 1
        const val TYPE_ADD = 2
    }

    private var mOnActionListener: OnActionListner? = null

    override fun getItemCount(): Int {
        return super.getItemCount() + 1
    }

    override fun getItemViewType(position: Int): Int {
        if (position < super.getItemCount()) {
            return TYPE_NORMAL
        }else{
            return TYPE_ADD
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            val viewholder = holder as ViewHolder
            val data = getData(position)
            viewholder.itemView.setOnClickListener {

            }

            viewholder.itemView.collection_news_title.text = data.title
            viewholder.itemView.collection_news_source.text = data.author
        }else{
            val viewholder = holder as AddViewHolder
            mOnActionListener?.OnLoading(viewholder.ll)
        }
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_NORMAL) {
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_collectnews,parent,false))
        }else{
            AddViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_add,parent,false))
        }
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class ViewHolder(itemview :View): RecyclerView.ViewHolder(itemview) {
        val title = itemview.findViewById<TextView>(R.id.collection_news_title)
        val source = itemview.findViewById<TextView>(R.id.collection_news_source)
        val time = itemview.findViewById<TextView>(R.id.collection_news_time)
    }

    class AddViewHolder(itemview: View): RecyclerView.ViewHolder(itemview) {
        val tv = itemview.findViewById<TextView>(R.id.tv_item_add_none)
        val ll = itemview.findViewById<LinearLayout>(R.id.ll_item_add_loading)
    }


    interface OnActionListner {
        fun OnLoading(loadingview: View)
    }
}