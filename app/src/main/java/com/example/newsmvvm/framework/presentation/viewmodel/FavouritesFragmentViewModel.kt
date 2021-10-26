package com.example.newsmvvm.framework.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsmvvm.business.domain.model.Article
import com.example.newsmvvm.business.repo.CacheRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesFragmentViewModel @Inject constructor(
    private val repo: CacheRepoImpl
) : ViewModel() {

    init {
        getArticles()
    }

    fun getArticles() = repo.getArticles()

    fun removeArticle(article: Article) {
        viewModelScope.launch {
            repo.removeArticle(article)
        }
    }
    fun saveArticle(article: Article) {
        viewModelScope.launch { repo.insertNew(article) }
    }
}

