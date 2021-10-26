package com.example.newsmvvm.framework.datasource.network.model

import android.os.Parcelable
import com.example.newsmvvm.business.domain.model.Source
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticleDTO(
    @SerializedName("source")
    @Expose
    val source: SourceDTO,
    @SerializedName("author")
    @Expose
    val author: String?,
    @SerializedName("title")
    @Expose
    val title: String?,
    @SerializedName("description")
    @Expose
    val description: String?,
    @SerializedName("url")
    @Expose
    val url: String,
    @SerializedName("urlToImage")
    @Expose
    val urlToImage: String?,
    @SerializedName("publishedAt")
    @Expose
    val publishedAt: String?,
    @SerializedName("content")
    @Expose
    val content: String?,
) : Parcelable