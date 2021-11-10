package dev.bigfootprint.wannawatch.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.bigfootprint.wannawatch.model.Movie
import dev.bigfootprint.wannawatch.network.MoviePagingSource
import dev.bigfootprint.wannawatch.network.TMDBApiService
import dev.bigfootprint.wannawatch.util.MovieDtoMapper
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

class Repo @Inject constructor(private var apiService: TMDBApiService,
                               private var movieMapper: MovieDtoMapper) {

    init {
        Timber.d("Repo initialized")
    }


    //Initial function to retrieve movies and test domain mapper
    suspend fun getMoviesFromNetwork(): MutableList<Movie> {
        Timber.d("Attempting to fetch movies...")
        val jsonObject = apiService.getMovies(1)
        val convertedMovieList = movieMapper.convertJsonToMovieObject(jsonObject)
        Timber.d("Number of movies on page: ${convertedMovieList.size}")
        return convertedMovieList
    }


     fun getPagedMoviesFromNetwork(): Flow<PagingData<Movie>> {
        Timber.d("getPagedMoviesFromNetwork called")
        val movies: Flow<PagingData<Movie>> = Pager(config = PagingConfig(
            pageSize = 20)
        ) {
            MoviePagingSource()
        }.flow
        return movies
    }

    suspend fun getMovieDetailsFromNetwork(movieId: String): Movie {
        Timber.d("getting movie details for $movieId")
        val movieDetailsJsonObject = apiService.getMovieDetails(movieId)
        //movie will get returned
        val movie = movieMapper.parseMovieDetailsJson(movieDetailsJsonObject)
        Timber.d("$movieDetailsJsonObject")
        Timber.d("$movie")
        return movie
    }


}