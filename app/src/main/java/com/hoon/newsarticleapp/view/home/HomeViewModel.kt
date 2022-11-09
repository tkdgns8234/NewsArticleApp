package com.hoon.newsarticleapp.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hoon.newsarticleapp.BaseViewModel
import com.hoon.newsarticleapp.data.model.ArticleModel
import com.hoon.newsarticleapp.data.repository.article.ArticleRepository
import com.hoon.newsarticleapp.data.repository.db.DatabaseRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val articleRepository: ArticleRepository,
    private val databaseRepository: DatabaseRepository
) : BaseViewModel() {

    private val _homeStateLiveData = MutableLiveData<HomeState>(HomeState.UnInitialized)
    val homeStateLiveData: LiveData<HomeState> get() = _homeStateLiveData

    var searchTextWatcher: String = ""

    fun fetchData(query: String) = viewModelScope.launch {

        val flow = articleRepository.fetchArticles(query)
        flow?.let {
            setState(HomeState.GetArticles(it))
        }
    }

    fun insertArticleInDB(articleModel: ArticleModel) = viewModelScope.launch {
        databaseRepository.insertArticle(articleModel)
    }

    private fun setState(state: HomeState) {
        _homeStateLiveData.value = state
    }

}