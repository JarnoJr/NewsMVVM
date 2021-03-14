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

}