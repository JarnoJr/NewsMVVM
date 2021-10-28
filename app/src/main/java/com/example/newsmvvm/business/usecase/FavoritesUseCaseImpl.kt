package com.example.newsmvvm.business.usecase

import com.example.newsmvvm.business.domain.model.Article
import com.example.newsmvvm.business.repo.CacheRepo
import com.example.newsmvvm.framework.datasource.cache.mapper.FavoriteMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesUseCaseImpl @Inject constructor(
    private val repo: CacheRepo,
    private val mapper: FavoriteMapper
) : FavoritesUseCase {

    override fun favorites(): Flow<List<Article>> {
        return repo.favorites().map {
            mapper.entityListToDomainList(it)
        }
    }
}