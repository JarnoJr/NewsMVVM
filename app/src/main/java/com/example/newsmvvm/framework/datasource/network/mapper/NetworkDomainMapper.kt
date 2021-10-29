package com.example.newsmvvm.framework.datasource.network.mapper

import com.example.newsmvvm.business.domain.model.Article
import com.example.newsmvvm.business.domain.util.DTOMapper
import com.example.newsmvvm.framework.datasource.network.model.ArticleDTO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkDomainMapper @Inject constructor():DTOMapper<ArticleDTO,Article> {
    override fun mapfromDto(dto: ArticleDTO): Article {
        return Article(
            author = dto.author ?: "",
            content = dto.content ?:"",
            description = dto.description ?: "",
            publishedAt = dto.publishedAt ?: "",
            title = dto.title ?: "",
            url = dto.url,
            urlToImage = dto.urlToImage ?: ""
        )
    }
    fun dtoListToDomainList(dtos: List<ArticleDTO>): List<Article> {
        return dtos.map { mapfromDto(it) }
    }
}