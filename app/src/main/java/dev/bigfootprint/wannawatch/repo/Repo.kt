package dev.bigfootprint.wannawatch.repo

import dev.bigfootprint.wannawatch.model.Movie
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


    suspend fun getMoviesFromNetwork(): List<Movie> {
        Timber.d("Attempting to fetch movies...")
        val jsonObject = apiService.getMovies(1)
        val convertedMovieList = movieMapper.convertJsonToMovieObject(jsonObject)
        Timber.d("Number of movies on page: ${convertedMovieList.size}")
        return convertedMovieList
    }

    suspend fun getMovieDetailsFromNetwork(){
        val jsonArray = apiService.getMovieDetails()
    }


}