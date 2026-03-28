package com.example.movies.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.data.local.entity.RemoteKeyEntity

@Dao
interface RemoteKeyDao {
    @Query("SELECT * FROM remote_keys WHERE movieId = :id")
    suspend fun getRemoteKeyByMovieId(id: Int): RemoteKeyEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeyEntity: List<RemoteKeyEntity>)

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}