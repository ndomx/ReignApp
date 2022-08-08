package com.ndomx.reign.db

import androidx.room.*

@Dao
interface PostDao {
    @Query("SELECT * FROM posts ORDER BY created_at DESC")
    suspend fun getAll(): List<Post>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg posts: Post)

    @Delete
    suspend fun deletePost(post: Post)
}