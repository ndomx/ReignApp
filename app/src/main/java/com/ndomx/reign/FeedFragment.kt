package com.ndomx.reign

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ndomx.reign.db.FeedDatabase
import com.ndomx.reign.dummy.generateRandomPosts

class FeedFragment() : Fragment() {
    private val feedAdapter = FeedRecyclerViewAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_feed_list, container, false)

        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = feedAdapter

                val db = FeedDatabase.db(context)
                db.getAllPosts { posts ->
                    feedAdapter.addPosts(*posts.toTypedArray())
                }
            }
        }

        return view
    }

    override fun onResume() {
        super.onResume()

        val db = context?.let { FeedDatabase.db(it) } ?: return
        val posts = generateRandomPosts(50)
        db.insertPost(*posts.toTypedArray()) {
            db.getAllPosts {
                feedAdapter.addPosts(*it.toTypedArray())
            }
        }
    }

    fun attachListener(listener: IPostListener) {
        feedAdapter.attachListener(listener)
    }

    companion object {
        @JvmStatic
        fun newInstance() = FeedFragment()
    }
}