package com.example.newsmvvm.framework.datasource.network.model

import com.example.newsmvvm.business.domain.model.Article
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseDTO(
    @SerializedName("status")
    @Expose
    val status: String,
    @SerializedName("totalResults")
    @Expose
    val totalResults: Int,
    @SerializedName("articles")
    @Expose
    val articles: List<ArticleDTO>
)