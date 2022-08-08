package com.ndomx.reign.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "posts")
data class Post(
    @PrimaryKey
    @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "author") var author: String,
    @ColumnInfo(name = "created_at") var createDate: Date,
    @ColumnInfo(name = "url") var url: String,
    @ColumnInfo(name = "title") var title: String
)
