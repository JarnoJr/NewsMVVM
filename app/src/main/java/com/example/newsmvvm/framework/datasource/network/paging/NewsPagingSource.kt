package com.example.newsmvvm.framework.datasource.network.paging

import android.content.Context
import android.content.pm.PackageManager
import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsmvvm.business.domain.model.Article
import com.example.newsmvvm.framework.datasource.network.NewsApi
import com.example.newsmvvm.framework.datasource.network.mapper.NetworkDomainMapper
import retrofit2.HttpException
import java.io.IOException


class NewsPagingSource(
    private val api: NewsApi,
    private val query: String,
    private val context: Context,
    private val mapper: NetworkDomainMapper
) : PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val position = params.key ?: 1
        return try {
            val response = api.searchNews(
                query,
                position,
                params.loadSize,
                apiKey = context.packageManager.getApplicationInfo(
                    context.packageName,
                    PackageManager.GET_META_DATA
                ).metaData["apiKey"].toString()
            )
            val articles = mapper.dtoListToDomainList(response.articles)
            val nextKey = if (articles.isEmpty()) {
                null
            } else {
                position + (params.loadSize / PAGE_SIZE)
            }
            LoadResult.Page(
                data = articles,
                prevKey = if (position == 1) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}