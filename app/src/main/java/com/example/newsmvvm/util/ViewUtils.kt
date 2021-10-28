package com.example.newsmvvm.util

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.newsmvvm.R
import com.google.android.material.snackbar.Snackbar


fun Fragment.showSnack(
    message: String,
    color: Int = R.color.error,
    callback: (() -> Unit)? = null
) {
    Snackbar.make(this.requireView(), message, Snackbar.LENGTH_LONG)
        .apply {
            setActionTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            if (callback != null) {
                setAction("Undo") {
                    callback.invoke()
                }
            }
        }
        .setBackgroundTint(ContextCompat.getColor(this.requireContext(), color))
        .show()
}