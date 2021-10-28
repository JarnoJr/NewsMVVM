package com.example.newsmvvm.framework.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsmvvm.business.domain.model.Article
import com.example.newsmvvm.business.usecase.ArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ArticleFragmentViewModel @Inject constructor(
    private val useCase: ArticleUseCase
) : ViewModel() {

    private val _article = MutableLiveData<Article?>()

    val article:LiveData<Article?> get() = _article

    fun getArticle(url: String) {
        viewModelScope.launch(Dispatchers.Main) {
          _article.value =  withContext(Dispatchers.IO){useCase.getArticle(url)}
        }
    }

    fun insertArticle(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.insertArticle(article)
        }
    }

    fun removeArticle(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.removeArticle(article)
        }
    }
}