package com.example.newsmvvm.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.newsmvvm.local.NewsDao
import com.example.newsmvvm.model.Article
import com.example.newsmvvm.network.RetroAPI
import com.example.newsmvvm.util.Consts.Companion.API_KEY
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repo @Inject constructor(
    private val retroAPI: RetroAPI,
    private val dao: NewsDao
) {
    fun getNews(countryCode: String, category: String): Pager<Int, Article> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NewsPagingSource(retroAPI, countryCode, category, API_KEY) }
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
            pagingSourceFactory = { SearchNewsPagingSource(retroAPI, query, sortBy, API_KEY) }
        )
    }
}