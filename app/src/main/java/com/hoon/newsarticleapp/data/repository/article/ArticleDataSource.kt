package com.hoon.newsarticleapp.data.repository.article

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hoon.newsarticleapp.data.api.ArticleApiService
import com.hoon.newsarticleapp.data.model.ArticleModel
import com.hoon.newsarticleapp.extensions.toModelList
import com.hoon.newsarticleapp.util.Constants.ARTICLE_PAGE_SIZE

class ArticleDataSource(
    private val service: ArticleApiService,
    private val query: String
): PagingSource<Int, ArticleModel>() {

    override fun getRefreshKey(state: PagingState<Int, ArticleModel>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleModel> {
        return try {
            val page = params.key?: 0
            val results = service.searchArticles(query, page).body()!!.toModelList()!!
            val nextPage = if(results.count() == ARTICLE_PAGE_SIZE) page + 1 else null

            val comparator : Comparator<ArticleModel> = compareBy { it.title.length }
            results.sortedWith(comparator)
            LoadResult.Page(data = results, nextKey = nextPage, prevKey = null)
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}