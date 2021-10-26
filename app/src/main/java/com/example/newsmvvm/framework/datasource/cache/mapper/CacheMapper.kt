package com.example.newsmvvm.framework.datasource.cache.mapper

import com.example.newsmvvm.business.domain.model.Article
import com.example.newsmvvm.business.domain.util.EntityMapper
import com.example.newsmvvm.framework.datasource.cache.database.model.ArticleEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CacheMapper @Inject constructor() : EntityMapper<ArticleEntity, Article> {

    override fun mapFromEntity(entity: ArticleEntity): Article {
        return Article(
            author = entity.author ?: "",
            content = entity.content ?: "",
            description = entity.description,
            publishedAt = entity.publishedAt,
            title = entity.title,
            url = entity.url,
            urlToImage = entity.urlToImage ?: ""
        )
    }

    override fun mapToEntity(domain: Article): ArticleEntity {
        return ArticleEntity(
            author = domain.author,
            content = domain.content,
            description = domain.description,
            publishedAt = domain.getFormattedDate(),
            title = domain.title,
            url = domain.url,
            urlToImage = domain.urlToImage
        )
    }

    fun entityListToDomainList(entities: List<ArticleEntity>): List<Article> {
        return entities.map { mapFromEntity(it) }
    }
}