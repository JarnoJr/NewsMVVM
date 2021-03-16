package com.example.newsmvvm.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsmvvm.model.Article
import com.example.newsmvvm.util.Conventers

@Database(
    entities = [Article::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(Conventers::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}