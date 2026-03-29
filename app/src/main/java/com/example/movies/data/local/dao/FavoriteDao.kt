package com.example.movies.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.movies.data.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {

    @Insert
    fun addFavorite(item: FavoriteEntity)

    @Delete
    fun removeFavorite(item: FavoriteEntity)

    @Query("SELECT EXISTS(SELECT * FROM favorites WHERE id = :id)")
    fun isFavorite(id: Int): Boolean
}