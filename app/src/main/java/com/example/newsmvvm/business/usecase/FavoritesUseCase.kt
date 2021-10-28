package com.example.newsmvvm.business.usecase

import com.example.newsmvvm.business.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface FavoritesUseCase {

    fun favorites():Flow<List<Article>>
}