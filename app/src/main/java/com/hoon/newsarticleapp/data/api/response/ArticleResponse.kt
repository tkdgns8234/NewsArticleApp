package com.hoon.newsarticleapp.data.api.response


import com.google.gson.annotations.SerializedName

data class ArticleResponse(
    @SerializedName("copyright")
    val copyright: String,
    @SerializedName("response")
    val response: Response,
    @SerializedName("status")
    val status: String
)