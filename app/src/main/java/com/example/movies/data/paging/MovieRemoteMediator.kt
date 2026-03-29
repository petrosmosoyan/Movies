package com.example.movies.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movies.data.local.database.AppDatabase
import com.example.movies.data.local.model.MovieWithFavorite
import com.example.movies.data.local.entity.RemoteKeyEntity
import com.example.movies.data.mapper.toEntity
import com.example.movies.data.remote.api.MovieApi
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val movieApi: MovieApi,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, MovieWithFavorite>() {
    override suspend fun load   (
        loadType: LoadType,
        state: PagingState<Int, MovieWithFavorite>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)

                    // 2. Get next page key
                    val nextKey = remoteKeys?.nextKey ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )

                    nextKey
                }
            }

            val result = movieApi.getMovies(page = loadKey)

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    appDatabase.remoteKeyDao().clearRemoteKeys()
                    appDatabase.moviesDao().clearMovies()
                }

                val endOfPaginationReached = (result.page ?: 0) >= (result.totalPages ?: 0)
                val nextKey = if (endOfPaginationReached) null else (result.page ?: 0) + 1
                val keys = result.movieDtos?.map {
                    RemoteKeyEntity(movieId = it.id, nextKey = nextKey)
                } ?: emptyList()

                val movies = result.movieDtos?.mapIndexed { index, dto ->
                    val position = ((loadKey - 1) * 20) + index // Assuming 20 items per page
                    dto.toEntity(orderIndex = position)
                }

                appDatabase.remoteKeyDao().insertAll(keys)
                appDatabase.moviesDao().insertAll(movies ?: emptyList())
            }

            MediatorResult.Success(
                endOfPaginationReached = (result.page ?: 0) >= (result.totalPages ?: 0)
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, MovieWithFavorite>
    ): RemoteKeyEntity? {

        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data
            ?.lastOrNull()
            ?.let { movie ->
                appDatabase.remoteKeyDao().getRemoteKeyByMovieId(movie.movie.id)
            }
    }
}