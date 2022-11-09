package com.hoon.newsarticleapp.extensions

import com.hoon.microdustapp.data.database.ArticleEntity
import com.hoon.newsarticleapp.data.api.response.ArticleResponse
import com.hoon.newsarticleapp.data.model.ArticleModel
import com.hoon.newsarticleapp.util.Constants

fun ArticleResponse.toModelList(): List<ArticleModel>? {
    return response.docs.map {
        ArticleModel(
            title = it.title,
            imageUrl = it.multimedia.firstOrNull()?.let {
              Constants.URL_NYTIMES + it.url
            },
            publicDate = it.pubDate,
            webUrl = it.webUrl
        )
    }
}

fun ArticleEntity.toModel(): ArticleModel {
    return ArticleModel(
        title = this.title,
        publicDate = this.publicDate,
        webUrl = this.webUrl,
        imageUrl = this.imageUrl
    )
}

fun ArticleModel.toEntity(): ArticleEntity {
    return ArticleEntity(
        title = this.title,
        publicDate = this.publicDate,
        webUrl = this.webUrl,
        imageUrl = this.imageUrl,
    )
}

fun List<ArticleEntity>.toModel(): List<ArticleModel> {
    return this.map { it.toModel() }
}