package com.ndomx.reign.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DeletedPostDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun markAsDeleted(post: DeletedPost)

    @Query("SELECT post_id FROM deleted WHERE post_id = :postId")
    suspend fun getDeletedPosts(postId: Int): List<Int>

    @Query("SELECT post_id FROM deleted")
    suspend fun getDeletedPosts(): List<Int>
}