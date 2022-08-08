package com.ndomx.reign

import com.ndomx.reign.db.Post

interface IPostListener {
    fun expandPost(post: Post)
    fun deletePost(post: Post)
}