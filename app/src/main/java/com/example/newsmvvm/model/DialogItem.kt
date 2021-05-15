package com.example.newsmvvm.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DialogItem(
    var title: String = "",
    var imageUrl: String = ""
):Parcelable


