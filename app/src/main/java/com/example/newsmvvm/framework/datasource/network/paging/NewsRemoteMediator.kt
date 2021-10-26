package com.example.newsmvvm.framework.datasource.network.paging

import android.content.Context
import android.content.pm.PackageManager
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.newsmvvm.framework.datasource.cache.database.AppDatabase
import com.example.newsmvvm.framework.datasource.cache.database.model.ArticleEntity
import com.example.newsmvvm.framework.datasource.cache.database.model.RemoteKeys
import com.example.newsmvvm.framework.datasource.network.NewsApi
import com.example.newsmvvm.framework.datasource.network.mapper.NetworkMapper
import com.example.newsmvvm.util.Constants.STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@ExperimentalPagingApi
class NewsRemoteMediator constructor(
    private val api: NewsApi,
    private val db: AppDatabase,
    private val mapper: NetworkMapper,
    private val context: Context,
    private val country: String,
    private val category: String
) : RemoteMediator<Int, ArticleEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>,
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
        }
        return try {
            val apiResponse = api.getTopNews(
                perPage  = state.config.pageSize,
                page = page,
                apiKey = (context.packageManager.getApplicationInfo(
                    context.packageName,
                    PackageManager.GET_META_DATA
                ).metaData["apiKey"].toString()),
                category = category,
                country = country
            )
            val articles = apiResponse.articles
            val endOfPaginationReached = articles.isEmpty()
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.remoteKeysDao().clearRemoteKeys()
                    db.articlesDao().clearArticles()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = articles.map {
                    RemoteKeys(id = it.url, prevKey = prevKey, nextKey = nextKey)
                }
                db.remoteKeysDao().insertAll(keys)
                db.articlesDao().insertAll(mapper.dtoListToEntityList(articles))
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            e.printStackTrace()
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            if (e.code() == 426) {
                MediatorResult.Success(endOfPaginationReached = true)
            } else {
                MediatorResult.Error(e)
            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ArticleEntity>): RemoteKeys? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { article ->
                db.remoteKeysDao().remoteKeysArticleId(article.url)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ArticleEntity>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { article ->
                db.remoteKeysDao().remoteKeysArticleId(article.url)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, ArticleEntity>,
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.url?.let { url ->
                db.remoteKeysDao().remoteKeysArticleId(url)
            }
        }
    }
}

