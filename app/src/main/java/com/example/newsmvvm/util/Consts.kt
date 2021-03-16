package com.example.newsmvvm.util

import com.example.newsmvvm.BuildConfig

class Consts {

    companion object {
        const val API_KEY = BuildConfig.ApiKey

        const val BASE_URL = "https://newsapi.org/"
        const val CONNECTION_TIMEOUT: Long = 30
        const val READ_TIMEOUT: Long = 30
        const val WRITE_TIMEOUT: Long = 30
        const val DB_NAME = "NewsDB"
        val CATEGORIES = arrayOf(
            "Business",
            "Entertainment",
            "General",
            "Health",
            "Science",
            "Sports",
            "Technology"
        )
        val COUNTRIES= arrayOf("ae", "ar", "at", "au", "be", "bg", "br", "ca", "ch", "cn", "co", "cu", "cz", "de", "eg", "fr", "gb", "gr",
            "hk", "hu", "id", "ie", "il", "in", "it", "jp", "kr", "lt", "lv", "ma", "mx", "my", "ng", "nl", "no", "nz",
            "ph", "pl", "pt", "ro", "rs", "ru", "sa", "se", "sg", "si", "sk", "th", "tr", "tw", "ua", "us", "ve", "za")
    }
}