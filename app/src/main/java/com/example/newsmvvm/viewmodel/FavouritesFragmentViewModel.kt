package com.example.newsmvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsmvvm.model.Article
import com.example.newsmvvm.repo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesFragmentViewModel @Inject constructor(
    private val repo: Repo
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

