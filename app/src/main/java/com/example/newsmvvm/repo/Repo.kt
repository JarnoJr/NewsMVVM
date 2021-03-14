package com.example.newsmvvm.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.newsmvvm.BuildConfig
import com.example.newsmvvm.network.RetroAPI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repo @Inject constructor(
    private val retroAPI: RetroAPI
) {

    fun getNews(countryCode:String,category:String,) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {NewsPagingSource(retroAPI,countryCode,category,BuildConfig.ApiKey)}
        ).liveData

}