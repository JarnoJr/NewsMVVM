package com.example.newsmvvm.business.repo

import android.content.Context
import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsmvvm.business.domain.model.Article
import com.example.newsmvvm.framework.datasource.network.NewsApi
import com.example.newsmvvm.framework.datasource.network.mapper.NetworkDomainMapper
import com.example.newsmvvm.framework.datasource.network.paging.NewsPagingSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchNewsRepoImpl @Inject constructor(
    private val api: NewsApi,
    private val mapper: NetworkDomainMapper,
    @ApplicationContext private val context: Context,
) : SearchNewsRepo {
    override fun getSearchResultStream(query: String): Flow<PagingData<Article>> = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { NewsPagingSource(api, query, context, mapper) }
    ).flow
}