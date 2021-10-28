package com.example.newsmvvm.framework.datasource.cache.mapper

import com.example.newsmvvm.business.domain.model.Article
import com.example.newsmvvm.business.domain.util.EntityMapper
import com.example.newsmvvm.framework.datasource.cache.database.model.FavoriteEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteMapper @Inject constructor() : EntityMapper<FavoriteEntity, Article> {
    override fun mapFromEntity(entity: FavoriteEntity?): Article? {
        return if(entity != null) {
            Article(
                author = entity.author ?: "",
                content = entity.content ?: "",
                description = entity.description,
                publishedAt = entity.publishedAt,
                title = entity.title,
                url = entity.url,
                urlToImage = entity.urlToImage ?: ""
            )
        }else {
            null
        }
    }

    override fun mapToEntity(domain: Article): FavoriteEntity {
        return FavoriteEntity(
            author = domain.author,
            content = domain.content,
            description = domain.description,
            publishedAt = domain.getFormattedDate(),
            title = domain.title,
            url = domain.url,
            urlToImage = domain.urlToImage
        )
    }
}