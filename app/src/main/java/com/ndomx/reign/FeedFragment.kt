package com.ndomx.reign

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.ndomx.reign.db.FeedDatabase
import com.ndomx.reign.db.Post
import com.ndomx.reign.dummy.generateRandomPosts

class FeedFragment() : Fragment(), IPostListener {
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
                onRefresh()
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

        // TODO: add listener in constructor
        feedAdapter.attachListener(this)

        val db = context?.let { FeedDatabase.db(it) } ?: return
        db.getAllPosts {
            feedAdapter.addPosts(*it.toTypedArray())
        }
    }

    override fun expandPost(post: Post) {
        findNavController().navigate(
            R.id.action_feedFragment_to_postFragment,
            bundleOf(PostFragment.ARG_URL to post.url)
        )
    }

    private fun loadRecyclerView(view: RecyclerView) {
        with(view) {
            layoutManager = LinearLayoutManager(context)
            adapter = feedAdapter

            val db = FeedDatabase.db(context)
            db.getAllPosts { posts ->
                feedAdapter.addPosts(*posts.toTypedArray())
            }

            val swipeListener = SwipeToDeleteCallback(context) { position ->
                onItemSwipe(position)
            }

            val itemTouchHelper = ItemTouchHelper(swipeListener)
            itemTouchHelper.attachToRecyclerView(view)
        }
    }

    private fun onRefresh() {
        Log.i("FeedFragment", "onRefresh called")
        context?.let {
            val repo = Repository.getInstance(it)
            repo.updateFeedData(it) { count ->
                showSnackbar("Downloaded $count posts")

                val db = FeedDatabase.db(it)
                db.getAllPosts { posts ->
                    feedAdapter.addPosts(*posts.toTypedArray())
                }

                refreshLayout?.isRefreshing = false
            }
        }
    }

    private fun showSnackbar(message: String) {
        refreshLayout?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun onItemSwipe(position: Int) {
        showSnackbar("Removing post at position $position")

        val post = feedAdapter.popItemAt(position)

        val db = context?.let { FeedDatabase.db(it) } ?: return
        db.markPostAsDeleted(post)
    }
}