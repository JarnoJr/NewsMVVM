package com.example.newsmvvm.di

import android.content.Context
import androidx.room.Room
import com.example.newsmvvm.framework.datasource.cache.database.AppDatabase
import com.example.newsmvvm.framework.datasource.cache.database.dao.ArticleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "NewsDb").fallbackToDestructiveMigration().build()
    }
    @Provides
    fun provideDao(appDatabase: AppDatabase): ArticleDao {
        return appDatabase.articlesDao()
    }
}