package com.example.newsmvvm.business.usecase

import com.example.newsmvvm.business.domain.model.Article
import com.example.newsmvvm.business.repo.CacheRepo
import com.example.newsmvvm.framework.datasource.cache.mapper.CacheMapper
import com.example.newsmvvm.framework.datasource.cache.mapper.FavoriteMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleUseCaseImpl @Inject constructor(
    private val mapper: FavoriteMapper,
    private val repo: CacheRepo
) : ArticleUseCase {
    override suspend fun getArticle(url: String): Article? {
        return mapper.mapFromEntity(repo.getArticle(url))
    }

    override suspend fun insertArticle(article: Article) {
        repo.insertArticle(mapper.mapToEntity(article))
    }

    override suspend fun removeArticle(article: Article) {
        repo.removeArticle(mapper.mapToEntity(article))
    }
}