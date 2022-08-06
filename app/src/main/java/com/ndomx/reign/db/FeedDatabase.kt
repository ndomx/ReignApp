package com.ndomx.reign.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Post::class], version = 1)
abstract class FeedDatabase : RoomDatabase() {
    companion object {
        private var INSTANCE: FeedDatabase? = null

        fun db(context: Context): FeedDatabase {
            return INSTANCE ?: Room.databaseBuilder(
                context, FeedDatabase::class.java, "reign-app"
            ).build()
        }
    }

    abstract fun postDao(): PostDao
}