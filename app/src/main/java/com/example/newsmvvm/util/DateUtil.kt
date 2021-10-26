package com.example.newsmvvm.util

import java.text.SimpleDateFormat
import java.util.*

fun String.convertToNewFormat(): String {
    val sourceFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val desFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val convertedDate = sourceFormat.parse(this)
    return desFormat.format(convertedDate)
}