package com.hoon.microdustapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ArticleEntity::class], version = 1)
abstract class ArticleDataBase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao

    companion object {
        const val DATABASE_NAME = "ArticleDataBase"

        fun build(context: Context): ArticleDataBase =
            Room.databaseBuilder(context, ArticleDataBase::class.java, DATABASE_NAME).build()
    }
}