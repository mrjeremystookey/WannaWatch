package dev.bigfootprint.wannawatch.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.bigfootprint.wannawatch.model.Movie
import dev.bigfootprint.wannawatch.repo.Repo
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


    private val _listOfMovies = mutableStateOf(emptyList<Movie>())
    val listOfMovies: State<List<Movie>> = _listOfMovies


    //Could be run on ViewModel init or on fragment's onCreateView
    private fun getListOfMovies(){
        Timber.d("getListOfMovies called")
        viewModelScope.launch {
            runCatching {
                repo.getMoviesFromNetwork()
            }.onFailure { error: Throwable ->
                Timber.d("fetching movies failed, $error")
            }.onSuccess { listOfMovies ->
                Timber.d("fetching movies successful")
                _listOfMovies.value = listOfMovies
            }
        }
    }


    //Occurs on movie selection
    fun getMovieDetails(){
        viewModelScope.launch {
            runCatching {
                repo.getMovieDetailsFromNetwork()
            }.onFailure { error: Throwable ->
                Timber.d("fetching movie details error, $error")
            }.onSuccess {
                Timber.d("fetching movie details successful")
            }
        }
    }



}