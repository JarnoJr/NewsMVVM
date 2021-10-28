package com.example.newsmvvm.business.repo

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.newsmvvm.business.domain.model.Article
import com.example.newsmvvm.framework.datasource.cache.database.model.ArticleEntity
import com.example.newsmvvm.framework.datasource.cache.database.model.FavoriteEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface CacheRepo {

    fun getArticles(countryCode: String, category: String):  Flow<PagingData<ArticleEntity>>

    suspend fun getArticle(url:String):FavoriteEntity?

    suspend fun insertArticle(article:FavoriteEntity)

    suspend fun removeArticle(article:FavoriteEntity)
}