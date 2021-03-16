package com.example.newsmvvm.viewmodel

import androidx.lifecycle.ViewModel
import com.example.newsmvvm.repo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouritesFragmentViewModel @Inject constructor(
    private val repo: Repo
) : ViewModel() {

    init {
        getArticles()
    }

    fun getArticles() = repo.getArticles()
}

