package com.example.newsmvvm.framework.datasource.cache.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsmvvm.business.domain.model.Article

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article):Long

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}