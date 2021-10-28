package com.example.newsmvvm.framework.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsmvvm.business.domain.model.Article
import com.example.newsmvvm.business.usecase.ArticleUseCase
import com.example.newsmvvm.business.usecase.FavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesFragmentViewModel @Inject constructor(
    private val useCase: FavoritesUseCase,
    private val articleUseCase: ArticleUseCase
) : ViewModel() {

    private val _favorites = MutableLiveData<List<Article>>()

    val favorites:LiveData<List<Article>> get() = _favorites

    init {
        favorites()
    }

    private fun favorites() {
        viewModelScope.launch {
            useCase.favorites().collectLatest {
               _favorites.value = it
            }
        }
    }

    fun removeArticle(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            articleUseCase.removeArticle(article)
        }
    }

    fun insertArticle(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            articleUseCase.insertArticle(article)
        }
    }
}

