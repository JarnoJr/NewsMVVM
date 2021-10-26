package com.example.newsmvvm.business.domain.util

import android.content.Context
import com.example.newsmvvm.R
import com.example.newsmvvm.business.domain.model.DialogItem
import com.neovisionaries.i18n.CountryCode
import timber.log.Timber

fun getCategories(context: Context): List<DialogItem> {
    return context.resources.getStringArray(R.array.categories)
        .map {
            return@map DialogItem(it)
        }
}

fun getCountries(context: Context): List<DialogItem> {
    return context.resources.getStringArray(R.array.countries)
        .map {
            val country = CountryCode.getByAlpha2Code(it.uppercase()).getName()
            Timber.d(country)
            return@map DialogItem(country)
        }
}