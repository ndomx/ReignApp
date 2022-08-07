package com.ndomx.reign

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ndomx.reign.db.Post
import java.lang.Exception
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

    fun downloadData(callback: (List<Post>) -> Unit) {
        val jsonRequest = JsonObjectRequest(Request.Method.GET, SERVER_URL, null,
            { response ->
                val hits = response.getJSONArray("hits")
                var result: List<Post>? = null
                try {
                    result = (hits.map { json ->
                        Post(
                            id = json.optInt("story_id", -1),
                            author = json.getString("author"),
                            createDate = Date(json.getLong("created_at_i")),
                            url = json.getString("story_url"),
                            title = json.getString("story_title")
                        )
                    })
                } catch (e: Exception) {
                    Log.e(LOG_TAG, "Error $e")
                    result = null
                }
                finally {
                    callback(result ?: emptyList())
                }
            },
            { error ->
                Log.e(LOG_TAG, "Failed to load with error: $error")
            }
        )

        queue.add(jsonRequest)
    }
}