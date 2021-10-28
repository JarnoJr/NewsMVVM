package com.example.newsmvvm.framework.datasource.cache.database.dao

import androidx.room.*
import com.example.newsmvvm.framework.datasource.cache.database.model.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(article: FavoriteEntity)

    @Delete
    fun delete(article: FavoriteEntity)

    @Query("SELECT * FROM favorites WHERE url =:url")
    fun get(url: String): FavoriteEntity?

    @Query("SELECT * FROM favorites")
    fun favorites(): Flow<List<FavoriteEntity>>
}