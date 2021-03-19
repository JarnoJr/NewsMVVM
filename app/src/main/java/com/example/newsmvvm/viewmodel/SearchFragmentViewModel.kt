package com.example.newsmvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.newsmvvm.repo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(
    private val repo: Repo
) : ViewModel() {
    private val currentQuery = MutableLiveData<String>()

    val news = currentQuery.switchMap { query ->
        repo.searchNews(query).liveData.cachedIn(viewModelScope)
    }
    fun setQuery(query: String) {
        currentQuery.value = query
    }


}