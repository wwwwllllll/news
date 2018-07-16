package com.wuruoye.news.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.wuruoye.library.adapter.WBaseRVAdapter
import com.wuruoye.library.util.DateUtil
import com.wuruoye.news.R
import com.wuruoye.news.model.UserCache
import com.wuruoye.news.model.bean.ArticleItem

/**
 * @Created : wuruoye
 * @Date : 2018/7/10 19:51.
 * @Description :
 */
class ArticleItemRVAdapter : WBaseRVAdapter<ArticleItem>() {
    companion object {
        const val TYPE_NORMAL = 1
        const val TYPE_ADD = 2
    }

    private var mOnActionListener: OnActionListener? = null
    private var mUserCache = UserCache.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_NORMAL) {
            ViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_news, parent, false))
        }else {
            AddViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_add, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_ADD) {
            val viewHolder = holder as AddViewHolder
            mOnActionListener?.onLoading(viewHolder.ll)
        }else {
            val viewHolder = holder as ViewHolder
            val item = getData(position)
            viewHolder.itemView.setOnClickListener {
                onItemClick(item)
            }

            with(viewHolder) {
                tvTitle.text = item.title
                tvForward.text = item.forward
                tvAuthor.text = item.author
                tvDate.text = DateUtil.formatTime(item.millis * 1000,
                        "YYYY-MM-dd HH:MM")

                if (item.bg_img.isNotEmpty() && !mUserCache.noImg)
                    Glide.with(ivBg)
                        .load(item.bg_img)
                        .into(ivBg)
                else ivBg.visibility = GONE
            }
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) {
            TYPE_NORMAL
        }else{
            TYPE_ADD
        }
    }

    fun setOnActionListener(listener: OnActionListener) {
        mOnActionListener = listener
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle = itemView.findViewById<TextView>(R.id.tv_item_news_title)
        val tvForward = itemView.findViewById<TextView>(R.id.tv_item_news_forward)
        val tvAuthor = itemView.findViewById<TextView>(R.id.tv_item_news_author)
        val tvDate = itemView.findViewById<TextView>(R.id.tv_item_news_date)
        val ivBg = itemView.findViewById<ImageView>(R.id.iv_item_news_bg)
    }

    class AddViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tv = itemView.findViewById<TextView>(R.id.tv_item_add_none)!!
        val ll = itemView.findViewById<LinearLayout>(R.id.ll_item_add_loading)!!
    }

    interface OnActionListener {
        fun onLoading(loadingView: View)
    }
}