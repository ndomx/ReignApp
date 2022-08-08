package com.ndomx.reign

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ndomx.reign.db.Post

class FeedRecyclerViewAdapter() : RecyclerView.Adapter<FeedRecyclerViewAdapter.ViewHolder>() {
    private val entries = mutableListOf<Post>()
    private var listener: IPostListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_feed, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = entries[position]

        val infoText = "${item.author} - ${item.createDate}"

        holder.articleTitle.text = item.title
        holder.articleInfo.text = infoText
    }

    override fun getItemCount(): Int = entries.size

    fun attachListener(postListener: IPostListener) {
        listener = postListener
    }

    fun addPosts(vararg posts: Post) {
        entries.clear()
        entries.addAll(posts)

        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val articleTitle: TextView = view.findViewById(R.id.feed_article_title)
        val articleInfo: TextView = view.findViewById(R.id.feed_article_info)
        private val articleContainer: LinearLayout = view.findViewById(R.id.feed_article_container)

        init {
            articleContainer.setOnClickListener {
                listener?.expandPost(entries[bindingAdapterPosition])
            }

            articleContainer.setOnLongClickListener {
                val index = bindingAdapterPosition
                listener?.deletePost(entries[index])

                notifyItemRangeRemoved(index, 1)

                true
            }
        }

        override fun toString(): String {
            return super.toString() + " '" + articleInfo.text + "'"
        }
    }
}