package com.example.newsmvvm.business.repo

import android.content.Context
import androidx.paging.*
import com.example.newsmvvm.framework.datasource.cache.database.AppDatabase
import com.example.newsmvvm.framework.datasource.cache.database.model.ArticleEntity
import com.example.newsmvvm.framework.datasource.cache.database.model.FavoriteEntity
import com.example.newsmvvm.framework.datasource.network.NewsApi
import com.example.newsmvvm.framework.datasource.network.mapper.NetworkMapper
import com.example.newsmvvm.framework.datasource.network.paging.NewsRemoteMediator
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalPagingApi
@Singleton
class CacheRepoImpl @Inject constructor(
    private val api: NewsApi,
    private val db: AppDatabase,
    private val mapper: NetworkMapper,
    @ApplicationContext private val context: Context
) : CacheRepo {
    override fun getArticles(
        country: String,
        category: String
    ): Flow<PagingData<ArticleEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 40,
                maxSize = 100,
                enablePlaceholders = false,
                prefetchDistance = 2
            ),
            remoteMediator = NewsRemoteMediator(
                api,
                db,
                mapper,
                context,
                country,
                category
            ),
            pagingSourceFactory = {
                (db.articlesDao().articles())
            }
        ).flow
    }

    override suspend fun getArticle(url: String): FavoriteEntity? {
        return db.favoritesDao().get(url)
    }

    override suspend fun insertArticle(article: FavoriteEntity) {
        db.favoritesDao().insert(article)
    }

    override suspend fun removeArticle(article: FavoriteEntity) {
        db.favoritesDao().delete(article)
    }
}