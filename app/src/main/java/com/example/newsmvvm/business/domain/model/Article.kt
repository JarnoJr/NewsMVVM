package com.example.newsmvvm.business.domain.model

import android.os.Parcelable
import com.example.newsmvvm.util.convertToNewFormat
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val source: Source? = null,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String?
) : Parcelable {
    fun getFormattedDate(): String {
        return publishedAt.convertToNewFormat()
    }
}