package com.example.newsmvvm.util

import com.example.newsmvvm.business.domain.model.Article

interface OnArticleClickListener {
    fun onArticleClick(article: Article)
}