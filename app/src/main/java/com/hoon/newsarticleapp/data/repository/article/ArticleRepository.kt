package com.hoon.newsarticleapp.data.repository.article

import androidx.paging.PagingData
import com.hoon.newsarticleapp.data.model.ArticleModel
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {

    suspend fun fetchArticles(query: String): Flow<PagingData<ArticleModel>>
}