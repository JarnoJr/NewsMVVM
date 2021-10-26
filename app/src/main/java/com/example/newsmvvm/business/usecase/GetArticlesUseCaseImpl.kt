package com.example.newsmvvm.business.usecase

import androidx.paging.PagingData
import com.example.newsmvvm.business.domain.model.Article
import com.example.newsmvvm.business.repo.CacheRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetArticlesUseCaseImpl @Inject constructor(
    private val cacheRepo:CacheRepo
):GetArticlesUseCase {
    override fun getArticles(countryCode: String, category: String): Flow<PagingData<Article>> {
        TODO("Not yet implemented")
    }
}