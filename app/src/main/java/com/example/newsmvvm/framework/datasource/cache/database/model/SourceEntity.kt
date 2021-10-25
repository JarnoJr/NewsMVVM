package com.example.newsmvvm.framework.datasource.cache.database.model

import android.os.Parcelable
import androidx.room.PrimaryKey


@kotlinx.parcelize.Parcelize
data class SourceEntity(
    @PrimaryKey
    val id: String,
    val name: String
) : Parcelable