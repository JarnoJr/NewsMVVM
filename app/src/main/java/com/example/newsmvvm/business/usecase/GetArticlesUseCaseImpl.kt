package com.example.newsmvvm.business.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.example.newsmvvm.business.domain.model.Article
import com.example.newsmvvm.business.repo.CacheRepo
import com.example.newsmvvm.framework.datasource.cache.mapper.CacheMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetArticlesUseCaseImpl @Inject constructor(
    private val cacheRepo: CacheRepo,
    private val mapper: CacheMapper
) : GetArticlesUseCase {
    override fun getArticles(countryCode: String, category: String): Flow<PagingData<Article>> {
        return cacheRepo.getArticles(countryCode, category)
            .map {
                it.map { entity ->
                    return@map mapper.mapFromEntity(entity)
                }
            }
    }
}