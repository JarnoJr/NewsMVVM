package com.example.newsmvvm.framework.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.newsmvvm.business.domain.model.Article
import com.example.newsmvvm.business.usecase.GetArticlesUseCase
import com.example.newsmvvm.util.PreferencesManager
import com.neovisionaries.i18n.CountryCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BreakingNewsViewModel
@Inject constructor(
    private val useCase: GetArticlesUseCase,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    lateinit var selectedCountry:String
    lateinit var selectedCategory: String

    val articles = preferencesManager.preferencesFlow
        .flatMapLatest {
            selectedCategory = it.category
            selectedCountry = it.country
            getArticles(it.country,it.category)
        }

    private fun getArticles(country: String, category: String): Flow<PagingData<Article>> {
        return useCase.getArticles(country, category)
    }

    fun updateCountry(country: String) {
        viewModelScope.launch(Dispatchers.IO) {
            preferencesManager.updateCountry(
                CountryCode.findByName(country)[0].toString().lowercase()
            )
        }
    }

    fun updateCategory(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            preferencesManager.updateCategory(category.lowercase())
        }
    }
}