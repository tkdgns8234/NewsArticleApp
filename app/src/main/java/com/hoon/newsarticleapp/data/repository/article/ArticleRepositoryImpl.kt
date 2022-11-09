package com.hoon.newsarticleapp.data.repository.article

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hoon.newsarticleapp.data.api.ArticleApiService
import com.hoon.newsarticleapp.data.model.ArticleModel
import com.hoon.newsarticleapp.util.Constants.ARTICLE_PAGE_SIZE
import kotlinx.coroutines.flow.Flow

class ArticleRepositoryImpl(
    private val articleApiService: ArticleApiService,
) : ArticleRepository {

    override suspend fun fetchArticles(query: String): Flow<PagingData<ArticleModel>> {
        return Pager(
            config = PagingConfig(ARTICLE_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { ArticleDataSource(articleApiService, query) }
        ).flow
    }
}