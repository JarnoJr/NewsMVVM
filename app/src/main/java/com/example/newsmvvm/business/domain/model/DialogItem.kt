package com.example.newsmvvm.business.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DialogItem(
    val text: String,
    var isChecked: Boolean = false
) : Parcelable


