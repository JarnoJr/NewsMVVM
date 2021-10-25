package com.example.newsmvvm.repo

import android.content.Context
import android.content.pm.PackageManager
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.newsmvvm.local.NewsDao
import com.example.newsmvvm.model.Article
import com.example.newsmvvm.network.RetroAPI
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repo @Inject constructor(
    private val retroAPI: RetroAPI,
    private val dao: NewsDao,
    @ApplicationContext private val context: Context
) {
    fun getNews(countryCode: String, category: String): Pager<Int, Article> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NewsPagingSource(retroAPI, countryCode, category, getApiKey()) }
        )
    }

    suspend fun insertNew(article: Article) = dao.upsert(article)
    suspend fun removeArticle(article: Article) {
        dao.deleteArticle(article)
    }

    fun getArticles() = dao.getAllArticles()

    fun searchNews(query: String, sortBy: String): Pager<Int, Article> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SearchNewsPagingSource(retroAPI, query, sortBy, getApiKey()) }
        )
    }

    private fun getApiKey(): String {
        val ai = context.packageManager.getApplicationInfo(
            context.packageName,
            PackageManager.GET_META_DATA
        )
        return ai.metaData["apiKey"].toString()
    }
}