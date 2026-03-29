package com.example.movies.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.movies.data.local.entity.MovieEntity
import com.example.movies.data.local.model.MovieWithFavorite
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Upsert
    suspend fun insertAll(list: List<MovieEntity>)

    @Query("""
        SELECT 
            movies.*, 
            (favorites.id IS NOT NULL) AS isFavorite 
        FROM movies 
        LEFT JOIN favorites ON movies.id = favorites.id 
        ORDER BY orderIndex ASC
    """)
    fun moviesPaging(): PagingSource<Int, MovieWithFavorite>

    @Query("SELECT * FROM movies WHERE id = :movieId")
    suspend fun getMovieById(movieId: Int): MovieEntity

    @Query("""
        SELECT movies.*, (favorites.id IS NOT NULL) AS isFavorite 
        FROM movies 
        LEFT JOIN favorites ON movies.id = favorites.id 
        WHERE movies.id = :movieId
    """)
    fun getMovieDetails(movieId: Int): Flow<MovieWithFavorite>

    @Query("""
        SELECT movies.*, 1 AS isFavorite 
        FROM movies 
        INNER JOIN favorites ON movies.id = favorites.id
    """)
    fun getFavoriteMovies(): Flow<List<MovieWithFavorite>>

    @Update
    suspend fun updateMovie(movieEntity: MovieEntity)

    @Query("DELETE FROM movies")
    suspend fun clearMovies()

}