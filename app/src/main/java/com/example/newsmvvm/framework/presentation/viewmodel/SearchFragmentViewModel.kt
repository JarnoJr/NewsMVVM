package com.example.newsmvvm.framework.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsmvvm.business.repo.CacheRepo
import com.example.newsmvvm.business.repo.CacheRepoImpl
import com.example.newsmvvm.util.DoubleTrigger
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(
    private val repo: CacheRepo
) : ViewModel() {
//    private val currentQuery = MutableLiveData<String>()
//    private val sortBy = MutableLiveData<String>()
//
//    init {
//        currentQuery.value = "android"
//        sortBy.value = "relevancy"
//    }
//
//    val news =
//        Transformations.switchMap(DoubleTrigger(currentQuery, sortBy)) {
////            repo.searchNews(it.first!!, it.second!!).liveData.cachedIn(viewModelScope)
//        }
//
//    fun setQuery(query: String) {
//        currentQuery.value = query
//    }
//
//    fun setChip(sortBy: String) {
//        this.sortBy.value = sortBy
//    }
}