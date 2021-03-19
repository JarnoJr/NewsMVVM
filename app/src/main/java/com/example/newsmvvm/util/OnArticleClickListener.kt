package com.example.newsmvvm.util

import com.example.newsmvvm.model.Article

interface OnArticleClickListener {
    fun onArticleClick(article: Article)
}