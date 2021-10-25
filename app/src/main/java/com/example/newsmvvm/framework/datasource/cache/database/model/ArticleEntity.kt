package com.example.newsmvvm.framework.datasource.cache.database.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.newsmvvm.business.domain.model.Source
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@kotlinx.parcelize.Parcelize
@Entity(tableName = "articles", indices = [Index(value = ["title"], unique = true)])
data class ArticleEntity(
    val source: Source,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
    @PrimaryKey
    var id: Int? = null,
    var isChecked: Boolean = false,
) : Parcelable