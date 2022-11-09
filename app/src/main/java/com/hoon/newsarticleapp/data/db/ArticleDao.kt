package com.hoon.microdustapp.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Query("select * from ArticleEntity")
    fun getAll(): Flow<List<ArticleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE) // 이미 존재하는경우 replace
    suspend fun insert(entity: ArticleEntity)

    @Query("DELETE FROM ArticleEntity WHERE webUrl = :webUrl")
    suspend fun delete(webUrl: String)
}