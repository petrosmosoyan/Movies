package com.example.movies.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.movies.data.local.dao.MovieDao
import com.example.movies.data.local.database.AppDatabase
import com.example.movies.data.mapper.toDetails
import com.example.movies.data.mapper.toEntity
import com.example.movies.data.mapper.toFavorite
import com.example.movies.data.mapper.toMovie
import com.example.movies.data.paging.MovieRemoteMediator
import com.example.movies.data.remote.api.MovieApi
import com.example.movies.domain.model.Details
import com.example.movies.domain.model.Favorite
import com.example.movies.domain.model.Movie
import com.example.movies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val movieApi: MovieApi,
    private val movieDao: MovieDao,
) : MovieRepository {

    override suspend fun refreshMovieDetails(movieId: Int) {
        val response = movieApi.getDetails(movieId = movieId)
        val movie = movieDao.getMovieById(movieId)

        movieDao.updateMovie(response.toEntity(movie.orderIndex))
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getMoviesPaging(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 5,
                initialLoadSize = 20
            ),
            remoteMediator = MovieRemoteMediator(
                movieApi = movieApi,
                appDatabase = appDatabase
            ),
            pagingSourceFactory = {
                appDatabase.moviesDao().moviesPaging()
            }
        ).flow.map { pagingData ->
            pagingData.map { it.toMovie() }
        }
    }

    override fun getMovieDetails(movieId: Int): Flow<Details> =
        movieDao.getMovieDetails(movieId).map { it.toDetails() }

    override fun getFavoriteMovies(): Flow<List<Favorite>> =
        movieDao.getFavoriteMovies().map { list ->
            list.map { it.toFavorite() }
        }
}