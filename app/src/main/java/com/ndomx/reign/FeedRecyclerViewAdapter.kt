package com.ndomx.reign

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ndomx.reign.db.models.Post

class FeedRecyclerViewAdapter(
    private val onPostClick: (Post) -> Unit
) : RecyclerView.Adapter<FeedRecyclerViewAdapter.ViewHolder>() {
    private val entries = mutableListOf<Post>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_feed, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = entries[position]

        val dateFormatted = formatDate(item.createDate)
        val infoText = "${item.author} - $dateFormatted"

        holder.articleTitle.text = item.title
        holder.articleInfo.text = infoText
    }

    override fun getItemCount(): Int = entries.size

    fun addPosts(vararg posts: Post) {
        entries.clear()
        entries.addAll(posts)

        /**
         * Because the method clears the whole list,
         * we have to call [notifyDataSetChanged]
         */
        notifyDataSetChanged()
    }

    fun popItemAt(position: Int): Post {
        val post = entries[position]
        entries.removeAt(position)

        notifyItemRemoved(position)

        return post
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val articleTitle: TextView = view.findViewById(R.id.feed_article_title)
        val articleInfo: TextView = view.findViewById(R.id.feed_article_info)
        private val articleContainer: LinearLayout = view.findViewById(R.id.feed_article_container)

        init {
            articleContainer.setOnClickListener {
                onPostClick(entries[bindingAdapterPosition])
            }
        }

        override fun toString(): String {
            return super.toString() + " '" + articleInfo.text + "'"
        }
    }
}