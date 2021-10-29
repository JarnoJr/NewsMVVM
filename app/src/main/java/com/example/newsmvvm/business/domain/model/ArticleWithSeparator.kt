package com.example.newsmvvm.business.domain.model

sealed class ArticleWithSeparator{
    data class ArticleItem(val article:Article):ArticleWithSeparator()
    data class Separator(val query:String):ArticleWithSeparator()
}
