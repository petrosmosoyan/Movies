package com.example.movies.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movies.data.local.dao.FavoriteDao
import com.example.movies.data.local.dao.MovieDao
import com.example.movies.data.local.dao.RemoteKeyDao
import com.example.movies.data.local.entity.FavoriteEntity
import com.example.movies.data.local.entity.MovieEntity
import com.example.movies.data.local.entity.RemoteKeyEntity

@Database(
    entities = [MovieEntity::class, FavoriteEntity::class, RemoteKeyEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun moviesDao(): MovieDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun remoteKeyDao(): RemoteKeyDao

}