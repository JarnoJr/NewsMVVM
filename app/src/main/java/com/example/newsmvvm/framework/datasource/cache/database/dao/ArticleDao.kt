package com.example.newsmvvm.framework.datasource.cache.database.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.example.newsmvvm.business.domain.model.Article
import com.example.newsmvvm.framework.datasource.cache.database.model.ArticleEntity

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles:List<ArticleEntity>)

    @Query("SELECT * FROM  articles")
    fun articles(): PagingSource<Int, ArticleEntity>

    @Query("DELETE FROM articles")
    suspend fun clearArticles()

}