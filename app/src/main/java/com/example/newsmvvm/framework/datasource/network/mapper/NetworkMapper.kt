package com.example.newsmvvm.framework.datasource.network.mapper

import com.example.newsmvvm.business.domain.util.DTOMapper
import com.example.newsmvvm.framework.datasource.cache.database.model.ArticleEntity
import com.example.newsmvvm.framework.datasource.network.model.ArticleDTO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkMapper @Inject constructor() : DTOMapper<ArticleDTO, ArticleEntity> {
    override fun mapToEntity(dto: ArticleDTO): ArticleEntity {
        return ArticleEntity(
            author = dto.author ?: "",
            content = dto.content ?:"",
            description = dto.description ?: "",
            publishedAt = dto.publishedAt,
            title = dto.title,
            url = dto.url,
            urlToImage = dto.urlToImage ?: ""
        )
    }

    fun dtoListToEntityList(dtos: List<ArticleDTO>): List<ArticleEntity> {
        return dtos.map { mapToEntity(it) }
    }
}