package com.hoon.newsarticleapp.data.repository.db

import com.hoon.newsarticleapp.data.model.ArticleModel
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {

    val articleFlow: Flow<List<ArticleModel>>

    suspend fun insertArticle(articleModel: ArticleModel)

    suspend fun deleteArticle(articleModel: ArticleModel)
}