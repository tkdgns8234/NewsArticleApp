package com.hoon.newsarticleapp.view.clip

import com.hoon.newsarticleapp.data.model.ArticleModel

sealed class ClipState {
    object Uninitialized : ClipState()

    sealed class Success : ClipState() {
        data class FetchData(val models: List<ArticleModel>) : Success()
    }

    data class Error(
        val message: String
    ) : ClipState()
}