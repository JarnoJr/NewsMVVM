package com.example.newsmvvm.repo

import androidx.paging.PagingSource
import com.example.newsmvvm.business.domain.model.Article
import com.example.newsmvvm.framework.datasource.network.RetroAPI
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE = 1

class NewsPagingSource(
    private val api: RetroAPI,
    private val countryCode: String,
    private val category: String,
    private val apiKey: String
) : PagingSource<Int, Article>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        try {
            val nextPageNumber = params.key ?: STARTING_PAGE
            val response = api.getTopNews(
                countryCode,
                category,
                nextPageNumber,
                20,
                apiKey
            )

            val articles = response.articles
            return LoadResult.Page(
                data = articles,
                prevKey = if (nextPageNumber == STARTING_PAGE) null else nextPageNumber - 1,
                nextKey = if (articles.isEmpty()) null else nextPageNumber + 1
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}