package com.wuruoye.news.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.wuruoye.library.adapter.WBaseRVAdapter
import com.wuruoye.library.util.DateUtil
import com.wuruoye.news.R
import com.wuruoye.news.model.bean.ArticleComment
import de.hdodenhof.circleimageview.CircleImageView

/**
 * @Created : wuruoye
 * @Date : 2018/7/13 16:52.
 * @Description :
 */
class CommentRVAdapter : WBaseRVAdapter<ArticleComment>(){
    companion object {
        const val TYPE_NORMAL = 1
        const val TYPE_ADD = 2
    }
    private var mOnActionListener: OnActionListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_NORMAL) {
            ViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_comment, parent, false))
        }else {
            AddViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_add, parent, false))
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_ADD) {
            val viewHolder = holder as AddViewHolder
            mOnActionListener?.onLoading(object : OnActionCallback {
                override fun onPraise(add: Boolean) {

                }

                override fun onComment(add: Boolean) {

                }

                override fun onNoMore() {
                    viewHolder.ll.visibility = GONE
                    viewHolder.tv.visibility = VISIBLE
                }

            })
        }else {
            val comment = getData(position)
            val viewHolder = holder as ViewHolder
            with(viewHolder) {
                val callback = object : OnActionCallback {
                    override fun onPraise(add: Boolean) {
                        if (add) {
                            comment.is_praise = true
                            comment.praise_num += 1
                            ivPraise.setImageResource(R.drawable.ic_heart_full)
                            tvPraise.text = comment.praise_num.toString()
                        }else {
                            comment.is_praise = false
                            comment.praise_num -= 1
                            ivPraise.setImageResource(R.drawable.ic_heart_not)
                            tvPraise.text = comment.praise_num.toString()
                        }
                    }

                    override fun onComment(add: Boolean) {
                        if (add) {
                            comment.comment_num += 1
                            tvComment.text = comment.comment_num.toString()
                        }
                    }

                    override fun onNoMore() {

                    }
                }

                Glide.with(civ)
                        .load(comment.user.avatar)
                        .into(civ)
                tvName.text = comment.user.name
                if (comment.parent != null) {
                    tvParent.visibility = VISIBLE
                    tvParent.text = "@${comment.parent!!.user.name}: ${comment.parent!!.content}"
                }else {
                    tvParent.visibility = GONE
                }
                tvContent.text = comment.content
                tvDate.text = DateUtil.formatTime(comment.time, "YYYY-MM-dd HH:SS")

                if (comment.is_praise) {
                    ivPraise.setImageResource(R.drawable.ic_heart_full)
                }else {
                    ivPraise.setImageResource(R.drawable.ic_heart_not)
                }
                tvPraise.text = comment.praise_num.toString()
                tvComment.text = comment.comment_num.toString()

                llPraise.setOnClickListener {
                    mOnActionListener?.onPraiseClick(callback, comment)
                }
                llComment.setOnClickListener {
                    mOnActionListener?.onCommentClick(callback, comment)
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

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val civ = itemView.findViewById<CircleImageView>(R.id.civ_item_comment)!!
        val tvName = itemView.findViewById<TextView>(R.id.tv_item_comment_user)
        val tvParent = itemView.findViewById<TextView>(R.id.tv_item_comment_parent)
        val tvContent = itemView.findViewById<TextView>(R.id.tv_item_comment_content)
        val tvDate = itemView.findViewById<TextView>(R.id.tv_item_comment_date)
        val llPraise = itemView.findViewById<LinearLayout>(R.id.ll_item_comment_praise)
        val ivPraise = itemView.findViewById<ImageView>(R.id.iv_item_comment_praise)
        val tvPraise = itemView.findViewById<TextView>(R.id.tv_item_comment_praise)
        val llComment = itemView.findViewById<LinearLayout>(R.id.ll_item_comment_comment)
        val ivComment = itemView.findViewById<ImageView>(R.id.iv_item_comment_comment)
        val tvComment = itemView.findViewById<TextView>(R.id.tv_item_comment_comment)
    }

    class AddViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv = itemView.findViewById<TextView>(R.id.tv_item_add_none)!!
        val ll = itemView.findViewById<LinearLayout>(R.id.ll_item_add_loading)!!
    }

    interface OnActionListener {
        fun onLoading(callback: OnActionCallback)
        fun onPraiseClick(callback: OnActionCallback, item: ArticleComment)
        fun onCommentClick(callback: OnActionCallback, item: ArticleComment)
    }

    interface OnActionCallback {
        fun onPraise(add: Boolean)
        fun onComment(add: Boolean)
        fun onNoMore()
    }
}