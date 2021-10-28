package com.example.newsmvvm.di

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.newsmvvm.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class GlideModule {

    @Provides
    fun provideCircularProgressDrawable(@ApplicationContext context: Context): CircularProgressDrawable {
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5F
        circularProgressDrawable.centerRadius = 30F
        circularProgressDrawable.setColorSchemeColors(
            ContextCompat.getColor(
                context,
                R.color.primaryColor
            )
        )
        circularProgressDrawable.start()
        return circularProgressDrawable
    }
}