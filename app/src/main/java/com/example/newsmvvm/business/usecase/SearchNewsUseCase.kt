package com.example.newsmvvm.business.usecase

import androidx.paging.PagingData
import com.example.newsmvvm.business.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface SearchNewsUseCase {

    fun getSearchResultStream(query: String): Flow<PagingData<Article>>
}