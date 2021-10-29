package com.example.newsmvvm.business.usecase

import androidx.paging.PagingData
import com.example.newsmvvm.business.domain.model.Article
import com.example.newsmvvm.business.repo.SearchNewsRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchNewsUseCaseImpl @Inject constructor(
    private val repo: SearchNewsRepo
) : SearchNewsUseCase {
    override fun getSearchResultStream(query: String): Flow<PagingData<Article>> =
        repo.getSearchResultStream(query)
}