package com.ndomx.reign.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Database(entities = [Post::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class FeedDatabase : RoomDatabase() {
    companion object {
        private var INSTANCE: FeedDatabase? = null

        fun db(context: Context): FeedDatabase {
            return INSTANCE ?: Room.inMemoryDatabaseBuilder(
                context, FeedDatabase::class.java
            ).build()
            /*
            return INSTANCE ?: Room.databaseBuilder(
                context, FeedDatabase::class.java, "reign-app.db"
            ).build()
            */
        }
    }

    abstract fun postDao(): PostDao

    fun getAllPosts(callback: (posts: List<Post>) -> Unit) = runBlocking {
        launch {
            callback(postDao().getAll())
        }
    }
}