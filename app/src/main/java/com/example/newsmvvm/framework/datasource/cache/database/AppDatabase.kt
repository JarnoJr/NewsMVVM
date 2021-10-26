package com.example.newsmvvm.framework.datasource.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsmvvm.business.domain.model.Article
import com.example.newsmvvm.framework.datasource.cache.database.dao.ArticleDao
import com.example.newsmvvm.framework.datasource.cache.database.dao.RemoteKeysDao
import com.example.newsmvvm.util.Conventers

@Database(
    entities = [Article::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(Conventers::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articlesDao(): ArticleDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}