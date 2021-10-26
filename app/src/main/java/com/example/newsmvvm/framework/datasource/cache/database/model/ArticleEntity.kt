package com.example.newsmvvm.framework.datasource.cache.database.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@kotlinx.parcelize.Parcelize
@Entity(tableName = "articles", indices = [Index(value = ["title"], unique = true)])
data class ArticleEntity(
    val author: String?,
    val title: String,
    val description: String?,
    @PrimaryKey
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?,
    var isChecked: Boolean = false,
) : Parcelable