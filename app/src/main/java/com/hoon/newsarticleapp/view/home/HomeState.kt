package com.hoon.newsarticleapp.view.home

import androidx.paging.PagingData
import com.hoon.newsarticleapp.data.model.ArticleModel
import kotlinx.coroutines.flow.Flow

sealed class HomeState {
    object UnInitialized : HomeState()

    data class GetArticles(
        val flow: Flow<PagingData<ArticleModel>>
    ) : HomeState()
}