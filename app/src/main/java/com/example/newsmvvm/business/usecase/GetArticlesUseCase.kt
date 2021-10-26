package com.example.newsmvvm.business.usecase

import androidx.paging.PagingData
import com.example.newsmvvm.business.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface GetArticlesUseCase {

    fun getArticles(countryCode: String, category: String): Flow<PagingData<Article>>
}