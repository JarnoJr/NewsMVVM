package com.example.newsmvvm.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "articles", indices = [Index(value = ["title"], unique = true)])

data class Article(
    @SerializedName("source")
    @Expose
    val source: Source,
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
    val url: String?,
    @SerializedName("urlToImage")
    @Expose
    val urlToImage: String?,
    @SerializedName("publishedAt")
    @Expose
    val publishedAt: String?,
    @SerializedName("content")
    @Expose
    val content: String?,
    @PrimaryKey
    var id: Int? = null,
    var isChecked: Boolean = false,
) : Parcelable