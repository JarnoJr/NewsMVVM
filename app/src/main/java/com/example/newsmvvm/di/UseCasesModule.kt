package com.example.newsmvvm.di

import com.example.newsmvvm.business.usecase.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UseCasesModule {

    @Binds
    fun bindGetArticleUseCase(getArticlesUseCaseImpl: GetArticlesUseCaseImpl):GetArticlesUseCase

    @Binds
    fun bindArticleUseCase(articleUseCaseImpl: ArticleUseCaseImpl):ArticleUseCase

    @Binds
    fun bindFavoritesUseCase(favoritesUseCaseImpl: FavoritesUseCaseImpl):FavoritesUseCase

    @Binds
    fun bindSearchNewsUseCase(searchNewsUseCaseImpl: SearchNewsUseCaseImpl):SearchNewsUseCase

}