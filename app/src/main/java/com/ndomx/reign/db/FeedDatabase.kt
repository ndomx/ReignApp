package com.ndomx.reign.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Database(entities = [Post::class, DeletedPost::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class FeedDatabase : RoomDatabase() {
    companion object {
        private var INSTANCE: FeedDatabase? = null

        fun db(context: Context): FeedDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context, FeedDatabase::class.java, "reign.db"
                ).build()

                return INSTANCE!!
            }
        }
    }

    abstract fun postDao(): PostDao
    abstract fun deletedDao(): DeletedPostDao

    fun getAllPosts(callback: (posts: List<Post>) -> Unit) = runBlocking {
        launch {
            callback(postDao().getAll())
        }
    }

    fun insertPost(vararg posts: Post, callback: () -> Unit) = runBlocking {
        launch {
            val deletedPosts = deletedDao().getDeletedPosts()
            val filtered = posts.filter { post ->
                post.id !in deletedPosts
            }

            postDao().insertAll(*filtered.toTypedArray())
            callback()
        }
    }

    fun markPostAsDeleted(post: Post) = runBlocking {
        launch {
            deletedDao().markAsDeleted(DeletedPost(0, post.id))
            postDao().deletePost(post)
        }
    }
}