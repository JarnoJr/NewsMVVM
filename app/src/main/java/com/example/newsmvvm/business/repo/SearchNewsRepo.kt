package com.example.newsmvvm.business.repo

import androidx.paging.PagingData
import com.example.newsmvvm.business.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface SearchNewsRepo {

    fun getSearchResultStream(query: String): Flow<PagingData<Article>>
}