package com.example.newsmvvm.framework.presentation.viewmodel

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.newsmvvm.business.usecase.GetArticlesUseCase
import com.example.newsmvvm.util.PreferencesManager
import com.neovisionaries.i18n.CountryCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BreakingNewsViewModel
@Inject constructor(
    private val useCase: GetArticlesUseCase,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    lateinit var selectedCountry: String
    lateinit var selectedCategory: String

    private val preferences = preferencesManager.preferencesFlow
        .map {
            selectedCategory = it.category
            selectedCountry = it.country
            it
        }.asLiveData()

    val articles = Transformations.switchMap(
        preferences
    ) {
        return@switchMap getArticles(it.country, it.category).cachedIn(viewModelScope).asLiveData()
    }


    private fun getArticles(country: String, category: String) =
        useCase.getArticles(country, category)


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