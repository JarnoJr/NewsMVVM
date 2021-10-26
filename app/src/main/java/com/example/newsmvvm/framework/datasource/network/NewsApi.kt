package com.example.newsmvvm.framework.datasource.network

import com.example.newsmvvm.business.domain.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("/v2/top-headlines")
    suspend fun getTopNews(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("page") page: Int,
        @Query("pageSize") perPage: Int,
        @Query("apiKey") apiKey: String,

        ): NewsResponse

    @GET("/v2/everything")
    suspend fun searchNews(
        @Query("q") query: String,
        @Query("sortBy") sortBy: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("apiKey") apiKey: String
    ): NewsResponse
}