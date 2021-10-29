package com.example.newsmvvm.business.domain.model

import com.example.newsmvvm.util.Constants.DEFAULT_QUERY

data class UiState(
    val query:String = DEFAULT_QUERY,
    val lastQueryScrolled:String = DEFAULT_QUERY,
    val hasNotScrolledForCurrentSearch:Boolean = false
)