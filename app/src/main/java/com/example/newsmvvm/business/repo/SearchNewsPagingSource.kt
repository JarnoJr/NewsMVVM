package com.example.newsmvvm.business.repo

import androidx.paging.PagingSource
import com.example.newsmvvm.business.domain.model.Article
import com.example.newsmvvm.framework.datasource.network.NewsApi
import retrofit2.HttpException
import java.io.IOException

class SearchNewsPagingSource(
    private val api: NewsApi,
    private val query: String,
    private val sortBy: String,
    private val apiKey: String
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = api.searchNews(query, sortBy, nextPageNumber, 20, apiKey)
            val articles = response.articles
            LoadResult.Page(
                data = articles,
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                nextKey = if (articles.isEmpty()) null else nextPageNumber + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }


}