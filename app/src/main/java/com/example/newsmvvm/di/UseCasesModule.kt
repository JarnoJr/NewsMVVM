package com.example.newsmvvm.di

import com.example.newsmvvm.business.usecase.GetArticlesUseCase
import com.example.newsmvvm.business.usecase.GetArticlesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UseCasesModule {

    @Binds
    fun bindGetArticleUseCase(getArticlesUseCaseImpl: GetArticlesUseCaseImpl):GetArticlesUseCase

}