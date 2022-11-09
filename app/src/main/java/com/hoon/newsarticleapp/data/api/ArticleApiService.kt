package com.hoon.newsarticleapp.data.api

import com.hoon.newsarticleapp.data.api.response.ArticleResponse
import com.hoon.newsarticleapp.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApiService {

    /**
     * @param query : search message
     * @param page : article result page, you can set maximum page 100
     */
    @GET("articlesearch.json?sort=newest&api-key=${Constants.ARTICLE_API_KEY}")
    suspend fun searchArticles(
        @Query("q") query: String,
        @Query("page") page: Int,
    ): Response<ArticleResponse>
}