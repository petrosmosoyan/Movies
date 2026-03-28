package com.example.movies.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.movies.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Upsert
    suspend fun insertAll(list: List<MovieEntity>)

    @Query("SELECT * FROM movies")
    fun moviesPaging(): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM movies WHERE id IN (:userIds)")
    fun getMoviesByIds(userIds: List<Int>): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovieDetails(movieId: Int): Flow<MovieEntity>

    @Update
    suspend fun updateMovie(movieEntity: MovieEntity)

    @Query("DELETE FROM movies")
    suspend fun clearMovies()

}