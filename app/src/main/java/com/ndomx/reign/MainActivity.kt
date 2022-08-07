package com.ndomx.reign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ndomx.reign.db.Post

class MainActivity : AppCompatActivity(), IPostListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val feedFragment = FeedFragment.newInstance()
        feedFragment.attachListener(this)
        loadFragment(feedFragment)
    }

    override fun onPostClick(post: Post) {
        showToast("Loading ${post.title}")
        val postFragment = PostFragment.newInstance(post.url)
        loadFragment(postFragment)
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment, fragment)
        transaction.commit()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}