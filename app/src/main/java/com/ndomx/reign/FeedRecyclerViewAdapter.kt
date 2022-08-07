package com.ndomx.reign

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ndomx.reign.db.Post

import com.ndomx.reign.dummy.DummyContent.DummyItem

class FeedRecyclerViewAdapter : RecyclerView.Adapter<FeedRecyclerViewAdapter.ViewHolder>() {
    private val entries = mutableListOf<Post>()

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

    fun addPosts(vararg posts: Post) {
        val oldCount = entries.size
        entries.addAll(posts)
        notifyItemRangeInserted(oldCount, posts.size)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val articleTitle: TextView = view.findViewById(R.id.feed_article_title)
        val articleInfo: TextView = view.findViewById(R.id.feed_article_info)

        override fun toString(): String {
            return super.toString() + " '" + articleInfo.text + "'"
        }
    }
}