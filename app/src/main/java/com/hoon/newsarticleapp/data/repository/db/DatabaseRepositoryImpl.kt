package com.hoon.newsarticleapp.data.repository.db

import com.hoon.microdustapp.data.database.ArticleDao
import com.hoon.newsarticleapp.data.model.ArticleModel
import com.hoon.newsarticleapp.extensions.toEntity
import com.hoon.newsarticleapp.extensions.toModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DatabaseRepositoryImpl(
    private val articleDao: ArticleDao,
    private val ioDispatcher: CoroutineDispatcher
) : DatabaseRepository {

    override val articleFlow: Flow<List<ArticleModel>> =
        articleDao.getAll().distinctUntilChanged()
            .map { it.toModel() }
            .flowOn(ioDispatcher)

    override suspend fun insertArticle(articleModel: ArticleModel) = withContext(ioDispatcher) {
        articleDao.insert(articleModel.toEntity())
    }

    override suspend fun deleteArticle(articleModel: ArticleModel) = withContext(ioDispatcher) {
        articleDao.delete(articleModel.webUrl)
    }
}