package com.example.newsmvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.newsmvvm.repo.Repo
import com.example.newsmvvm.util.DoubleTrigger
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class BreakingNewsViewModel
@Inject constructor(
    private val repo: Repo
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
}