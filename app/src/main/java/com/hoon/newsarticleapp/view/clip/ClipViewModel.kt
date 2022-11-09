package com.hoon.newsarticleapp.view.clip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hoon.newsarticleapp.BaseViewModel
import com.hoon.newsarticleapp.data.model.ArticleModel
import com.hoon.newsarticleapp.data.repository.db.DatabaseRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ClipViewModel(
    private val databaseRepository: DatabaseRepository
) : BaseViewModel() {

    private val _clipViewModelLiveData = MutableLiveData<ClipState>(ClipState.Uninitialized)
    val clipViewModelLiveData: LiveData<ClipState> get() = _clipViewModelLiveData

    var searchTextWatcher: String = ""

    fun observeArticleFlow() {
        databaseRepository.articleFlow
            .onEach {
                val filteredList = it.filter { model ->
                    if (searchTextWatcher.isNullOrEmpty()) {
                        true
                    } else {
                        model.title.contains(searchTextWatcher, ignoreCase = true)
                    }
                }
                setState(ClipState.Success.FetchData(filteredList))
            }
            .launchIn(viewModelScope)
    }

    fun deleteArticleInDB(model: ArticleModel) = viewModelScope.launch {
        databaseRepository.deleteArticle(model)
    }

    private fun setState(state: ClipState) {
        _clipViewModelLiveData.value = state
    }
}