package com.example.newsmvvm.di

import androidx.paging.ExperimentalPagingApi
import com.example.newsmvvm.business.repo.CacheRepo
import com.example.newsmvvm.business.repo.CacheRepoImpl
import com.example.newsmvvm.business.repo.SearchNewsRepo
import com.example.newsmvvm.business.repo.SearchNewsRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@ExperimentalPagingApi
@Module
@InstallIn(SingletonComponent::class)
interface RepoModule {

    @Binds
    fun bindCacheRepo(cacheRepoImpl: CacheRepoImpl): CacheRepo

    @Binds
    fun bindSearchNewsRepo(searchNewsRepoImpl: SearchNewsRepoImpl):SearchNewsRepo
}