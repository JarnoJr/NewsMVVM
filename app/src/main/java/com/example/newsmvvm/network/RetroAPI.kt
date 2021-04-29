package com.example.newsmvvm.network

import com.example.newsmvvm.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RetroAPI {

    @GET("/v2/top-headlines")
    suspend fun getTopNews(
        @Query("country") countryCode: String,
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