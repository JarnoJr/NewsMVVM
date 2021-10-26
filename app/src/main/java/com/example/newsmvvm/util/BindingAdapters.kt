package com.example.newsmvvm.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.newsmvvm.R

@BindingAdapter("load")
fun ImageView.load(
    url: String?
) {
    url?.let {
        Glide.with(this).load(url)
            .error(R.drawable.ic_camera).into(this)
    }
}