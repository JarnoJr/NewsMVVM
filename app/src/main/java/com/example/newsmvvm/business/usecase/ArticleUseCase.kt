package com.example.newsmvvm.business.usecase

import com.example.newsmvvm.business.domain.model.Article

interface ArticleUseCase {
   suspend fun getArticle(url:String): Article?

   suspend fun insertArticle(article:Article)

   suspend fun removeArticle(article: Article)
}