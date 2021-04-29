package com.example.newsmvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsmvvm.model.Article
import com.example.newsmvvm.repo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleFragmentViewModel @Inject constructor(
    private val repo: Repo
) : ViewModel() {

    fun insertArticle(article: Article) {
        viewModelScope.launch {
            article.id = repo.insertNew(article).toInt()
        }
    }

    fun removeArticle(article: Article) {
        viewModelScope.launch {
            repo.removeArticle(article)
        }
    }
}