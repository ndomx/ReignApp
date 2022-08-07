package com.ndomx.reign

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ndomx.reign.db.FeedDatabase
import com.ndomx.reign.dummy.generateRandomPosts

class FeedFragment() : Fragment() {
    private val feedAdapter = FeedRecyclerViewAdapter()

    private var refreshLayout: SwipeRefreshLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_feed_list, container, false)
        if (view is SwipeRefreshLayout) {
            refreshLayout = view
            view.setOnRefreshListener {
                Log.i("FeedFragment", "onRefresh called")
            }

            val recyclerView = view.findViewById<RecyclerView>(R.id.feed_recycler_view)
            loadRecyclerView(recyclerView)
        } else if (view is RecyclerView) {
            loadRecyclerView(view)
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

    private fun loadRecyclerView(view: RecyclerView) {
        with(view) {
            layoutManager = LinearLayoutManager(context)
            adapter = feedAdapter

            val db = FeedDatabase.db(context)
            db.getAllPosts { posts ->
                feedAdapter.addPosts(*posts.toTypedArray())
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FeedFragment()
    }
}