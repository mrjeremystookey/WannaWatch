package dev.bigfootprint.wannawatch.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.bigfootprint.wannawatch.model.Movie
import dev.bigfootprint.wannawatch.network.MoviePagingSource
import dev.bigfootprint.wannawatch.repo.Repo
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repo: Repo) : ViewModel() {


    init {
        Timber.d("MovieListViewModel initialized")
        getListOfMovies()
    }


    private val _listOfMovies = mutableStateOf(mutableListOf<Movie>())
    val listOfMovies: State<MutableList<Movie>> = _listOfMovies


    //displays parsed Movie object
    var selectedMovie: MutableState<Movie> = mutableStateOf(Movie())


    //Could be run on ViewModel init or on fragment's onCreateView
    private fun getListOfMovies(){
        Timber.d("getListOfMovies called")

        viewModelScope.launch {

            //List of movies
            runCatching {
                repo.getMoviesFromNetwork()
            }.onFailure { error: Throwable ->
                Timber.d("fetching movies failed, $error")
            }.onSuccess { listOfMovies ->
                Timber.d("fetching movies successful")
                _listOfMovies.value = listOfMovies
            }

            //Paged movies
            /*repo.getPagedMoviesFromNetwork().collect { it ->
                it.map { movie ->
                    listOfMovies.value.add(movie)
                    Timber.d("Movie found: ${movie.title}")
                }
            }*/
        }
    }


    //Occurs on movie selection
    fun getMovieDetails(movieId: String){
        viewModelScope.launch {
            runCatching {
                val movie = selectedMovie.value.movieId?.let { repo.getMovieDetailsFromNetwork(it) }

                Timber.d("Movie deets: $movie")
                if (movie != null) {
                    selectedMovie.value = movie
                }
            }.onFailure { error: Throwable ->
                Timber.d("fetching movie details error, $error")
            }.onSuccess {
                Timber.d("fetching movie details successful")
            }
        }
    }



}