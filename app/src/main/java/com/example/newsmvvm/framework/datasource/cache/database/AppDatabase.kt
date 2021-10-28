package com.example.newsmvvm.framework.datasource.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsmvvm.framework.datasource.cache.database.dao.ArticleDao
import com.example.newsmvvm.framework.datasource.cache.database.dao.FavoritesDao
import com.example.newsmvvm.framework.datasource.cache.database.dao.RemoteKeysDao
import com.example.newsmvvm.framework.datasource.cache.database.model.ArticleEntity
import com.example.newsmvvm.framework.datasource.cache.database.model.FavoriteEntity
import com.example.newsmvvm.framework.datasource.cache.database.model.RemoteKeys
import com.example.newsmvvm.util.Conventers

@Database(
    entities = [ArticleEntity::class, RemoteKeys::class, FavoriteEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Conventers::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritesDao():FavoritesDao
    abstract fun articlesDao(): ArticleDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}