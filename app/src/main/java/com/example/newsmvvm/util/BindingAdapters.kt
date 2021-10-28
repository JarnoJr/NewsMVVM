package com.example.newsmvvm.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.newsmvvm.R

@BindingAdapter(value = ["load", "circularProgressDrawable"], requireAll = true)
fun ImageView.load(
    url: String?,
    circularProgressDrawable: CircularProgressDrawable
) {
    url?.let {
        Glide.with(this).load(url)
            .placeholder(circularProgressDrawable)
            .error(R.drawable.ic_camera).into(this)
    }
}