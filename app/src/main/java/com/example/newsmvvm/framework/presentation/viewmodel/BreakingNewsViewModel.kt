package com.example.newsmvvm.framework.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.newsmvvm.business.domain.model.Article
import com.example.newsmvvm.business.usecase.GetArticlesUseCase
import com.neovisionaries.i18n.CountryCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject


@HiltViewModel
class BreakingNewsViewModel
@Inject constructor(
    private val useCase: GetArticlesUseCase
) : ViewModel() {

    private val country = MutableStateFlow("us")

    private val category = MutableStateFlow("general")

    val articles = combine(
        country,
        category,
        ::Pair
    ).flatMapLatest {
        getArticles(it.first, it.second)
    }

    private fun getArticles(country: String, category: String): Flow<PagingData<Article>> {
        return useCase.getArticles(country, category)
    }

    fun setCountry(country: String) {
        this.country.value = CountryCode.findByName(country)[0].toString().lowercase()
    }

    fun setCategory(category: String) {
        this.category.value = category
    }
}