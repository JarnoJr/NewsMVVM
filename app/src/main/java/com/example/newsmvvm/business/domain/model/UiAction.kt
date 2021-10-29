package com.example.newsmvvm.business.domain.model

sealed class UiAction {
    data class Search(val query:String):UiAction()
    data class Scroll(val currentQuery:String):UiAction()
}