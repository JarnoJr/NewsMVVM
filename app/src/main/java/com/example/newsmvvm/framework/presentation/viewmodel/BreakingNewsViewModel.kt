package com.example.newsmvvm.framework.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.newsmvvm.business.domain.model.DialogItem
import com.example.newsmvvm.business.repo.CacheRepoImpl
import com.example.newsmvvm.util.Constants
import com.example.newsmvvm.util.DoubleTrigger
import com.neovisionaries.i18n.CountryCode
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class BreakingNewsViewModel
@Inject constructor(
    private val repo: CacheRepoImpl
) : ViewModel() {

    private val countryCode = MutableLiveData<String>()
    private val category = MutableLiveData<String>()

    init {
        countryCode.value = "us"
        category.value = "general"
    }

    val news =
        Transformations.switchMap(DoubleTrigger(countryCode, category)) {
            repo.getNews(it.first!!, it.second!!).liveData.cachedIn(viewModelScope)
        }

    fun setCountry(country: String) {
        countryCode.value = country
    }

    fun setCategory(category: String) {
        this.category.value = category
    }

    fun getCountry(): String = countryCode.value!!.toUpperCase()

    fun getCategory(): String = category.value!!.capitalize()

    fun displayCategories(): List<DialogItem> {
        val categories = mutableListOf<DialogItem>()
        for (i in Constants.CATEGORIES.indices) {
            val category = DialogItem()
            category.title = Constants.CATEGORIES[i]
            category.imageUrl = Constants.CATEGORIES[i]
            categories.add(category)
        }
        return categories
    }

    fun displayCountries(): List<DialogItem> {
        val countries = mutableListOf<DialogItem>()
        for (i in Constants.COUNTRIES.indices) {
            val country = DialogItem()
            val cc = CountryCode.getByAlpha2Code(Constants.COUNTRIES[i].toUpperCase())
            val countryName = cc.getName()
            country.title = countryName
            country.imageUrl = cc.toString().toLowerCase()
            countries.add(country)
        }
        return countries
    }
}