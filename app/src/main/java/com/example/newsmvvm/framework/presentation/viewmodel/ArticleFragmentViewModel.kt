package com.example.newsmvvm.framework.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsmvvm.business.domain.model.Article
import com.example.newsmvvm.business.repo.CacheRepo
import com.example.newsmvvm.business.repo.CacheRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleFragmentViewModel @Inject constructor(
    private val repo: CacheRepo
) : ViewModel() {

//    fun insertArticle(article: Article) {
//        viewModelScope.launch {
//            article.id = repo.insertNew(article).toInt()
//        }
//    }
//
//    fun removeArticle(article: Article) {
//        viewModelScope.launch {
//            repo.removeArticle(article)
//        }
//    }
}