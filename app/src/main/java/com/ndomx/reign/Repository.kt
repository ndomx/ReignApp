package com.ndomx.reign

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ndomx.reign.db.FeedDatabase
import com.ndomx.reign.db.Post
import org.json.JSONObject
import java.util.*

class Repository(context: Context) {
    private val queue = Volley.newRequestQueue(context)

    companion object {
        private const val SERVER_URL = "https://hn.algolia.com/api/v1/search_by_date?query=mobile"
        private const val LOG_TAG = "Repository"

        private var INSTANCE: Repository? = null
        fun getInstance(context: Context): Repository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE = Repository(context)
                return INSTANCE!!
            }
        }
    }

    fun updateFeedData(context: Context, callback: (Int) -> Unit) {
        val jsonRequest = JsonObjectRequest(Request.Method.GET, SERVER_URL, null,
            { response ->
                val hits = response.getJSONArray("hits")
                val posts = mutableListOf<Post>()
                val db = FeedDatabase.db(context)

                hits.forEach { json ->
                    val post = parsePost(json)
                    if (post != null) {
                        posts.add(post)
                    }
                }

                db.insertPost(*posts.toTypedArray()) {
                    callback(posts.size)
                }
            },
            { error ->
                Log.e(LOG_TAG, "Failed to load with error: $error")
            }
        )

        queue.add(jsonRequest)
    }

    private fun parsePost(json: JSONObject): Post? {
        val postId = json.optInt("story_id", -1)
        val author = json.getString("author")
        val createTimestamp = json.optLong("created_at_i", -1) * 1000
        val url = json.getString("story_url")
        val title = json.getString("story_title")

        if (postId < 0) return null
        if (author == "null") return null
        if (createTimestamp < 0) return null
        if (url == "null") return null
        if (title == "null") return null

        return Post(
            id = postId,
            author = author,
            createDate = Date(createTimestamp),
            url = url,
            title = title
        )
    }
}