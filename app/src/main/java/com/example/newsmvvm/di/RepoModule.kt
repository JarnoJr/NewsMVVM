package com.example.newsmvvm.di

import androidx.paging.ExperimentalPagingApi
import com.example.newsmvvm.business.repo.CacheRepo
import com.example.newsmvvm.business.repo.CacheRepoImpl
import dagger.Binds
import dagger.Module

@ExperimentalPagingApi
@Module
interface RepoModule {

    @Binds
    fun bindCacheRepo(cacheRepoImpl: CacheRepoImpl): CacheRepo
}