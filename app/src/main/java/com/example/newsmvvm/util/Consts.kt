package com.example.newsmvvm.util

import com.example.newsmvvm.BuildConfig

class Consts {

    companion object {
        const val API_KEY = BuildConfig.ApiKey

        const val BASE_URL = "https://newsapi.org/"
        const val CONNECTION_TIMEOUT: Long = 30
        const val READ_TIMEOUT: Long = 30
        const val WRITE_TIMEOUT: Long = 30
    }
}