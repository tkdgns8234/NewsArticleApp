package com.hoon.microdustapp.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArticleEntity(
    @PrimaryKey val webUrl: String,
    @ColumnInfo val title: String,
    @ColumnInfo val publicDate: String,
    @ColumnInfo val imageUrl: String?,
)