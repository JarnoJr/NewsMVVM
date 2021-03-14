package com.example.newsmvvm.util

import android.content.Context
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlideUtil @Inject constructor() {

    fun progressUtil(context:Context):CircularProgressDrawable{
        val circularProgressDrawable:CircularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        return circularProgressDrawable
    }
}