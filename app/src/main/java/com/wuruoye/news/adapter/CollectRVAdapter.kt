package com.wuruoye.news.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TextView
import com.wuruoye.library.adapter.WBaseRVAdapter
import com.wuruoye.library.util.DateUtil
import com.wuruoye.news.R
import com.wuruoye.news.model.bean.ArticleCollect
import com.wuruoye.news.model.widget.EventHorizontalScrollView

/**
 * @Created : wuruoye
 * @Date : 2018/7/14 19:49.
 * @Description :
 */
class CollectRVAdapter : WBaseRVAdapter<ArticleCollect>() {
    companion object {
        const val TYPE_NORMAL = 1
        const val TYPE_ADD = 2
    }
    private var mOnActionListener: OnActionListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_NORMAL) {
            ViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_collect, parent, false))
        }else {
            AddViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_add, parent, false))
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_ADD) {
            val viewHolder = holder as AddViewHolder
            mOnActionListener?.onLoadMore(object : OnActionCallback {
                override fun onNoMore() {
                    viewHolder.ll.visibility = GONE
                    viewHolder.tv.visibility = VISIBLE
                }
            })
        }else {
            val viewHolder = holder as ViewHolder
            val collect = getData(position)

            with(viewHolder) {
                close()
                tvTitle.text = collect.content.title
                tvAuthor.text = collect.content.author
                tvDate.text = DateUtil.formatTime(collect.time, "YYYY-MM-dd HH:mm")
                tvCancel.text = "取\t消\n收\t藏"
                tvCancel.setOnClickListener {
                    mOnActionListener?.onCancelClick(collect)
                }
                ll.setOnClickListener {
                    onItemClick(collect)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) {
            TYPE_NORMAL
        }else {
            TYPE_ADD
        }
    }

    fun setOnActionListener(listener: OnActionListener) {
        mOnActionListener = listener
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            EventHorizontalScrollView.OnTouchListener {
        companion object {
            private val sOpenList = hashSetOf<ViewHolder>()
        }
        val ll = itemView.findViewById<LinearLayout>(R.id.ll_item_collect)!!
        val tvTitle = itemView.findViewById<TextView>(R.id.tv_item_collect_title)!!
        val tvAuthor = itemView.findViewById<TextView>(R.id.tv_item_collect_author)!!
        val tvDate = itemView.findViewById<TextView>(R.id.tv_item_collect_date)!!
        val tvCancel = itemView.findViewById<TextView>(R.id.tv_item_collect_cancel)!!

        init {
            itemView.post {
                val layoutParams = ll.layoutParams
                        as LinearLayout.LayoutParams
                layoutParams.width = itemView.width
                ll.layoutParams = layoutParams
            }
            val view = itemView as EventHorizontalScrollView
            view.setOnTouchListener(this)
        }

        fun open() {
            for (item in sOpenList) {
                if (item != this) {
                    item.close()
                }
            }

            val view = itemView as HorizontalScrollView
            view.post {
                view.smoothScrollTo(tvCancel.width, 0)
            }

            sOpenList.add(this)
        }

        fun close() {
            val view = itemView as HorizontalScrollView
            view.post {
                view.smoothScrollTo(0, 0)
            }
        }

        override fun onTouchUp() {
            val view = itemView as HorizontalScrollView
            val isOpen = view.scrollX >= tvCancel.width / 2
            if (isOpen) {
                open()
            }else {
                close()
            }
        }
    }

    class AddViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv = itemView.findViewById<TextView>(R.id.tv_item_add_none)
        val ll = itemView.findViewById<LinearLayout>(R.id.ll_item_add_loading)
    }

    interface OnActionListener {
        fun onCancelClick(item: ArticleCollect)
        fun onLoadMore(callback: OnActionCallback)
    }

    interface OnActionCallback {
        fun onNoMore()
    }
}