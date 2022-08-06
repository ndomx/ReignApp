package com.ndomx.reign.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "posts")
data class Post(
    @PrimaryKey var id: Int,
    var author: String,
    @ColumnInfo(name = "created_at") var createDate: Date,
    var url: String,
    var title: String
)
