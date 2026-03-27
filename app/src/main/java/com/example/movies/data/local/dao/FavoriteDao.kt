package com.example.movies.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.movies.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM ")
    fun getFavorites(): Flow<>

    @Insert
    fun addFavorite(item: MovieEntity)
}