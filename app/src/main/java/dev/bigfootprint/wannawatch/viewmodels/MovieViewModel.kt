package dev.bigfootprint.wannawatch.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.bigfootprint.wannawatch.model.Movie
import dev.bigfootprint.wannawatch.network.MoviePagingSource
import dev.bigfootprint.wannawatch.repo.Repo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repo: Repo, private val pagingSource: MoviePagingSource) : ViewModel() {



    private val _listOfMovies = mutableStateOf(mutableListOf<Movie>())
    val listOfMovies: State<MutableList<Movie>> = _listOfMovies

    var selectedMovie: MutableState<Movie> = mutableStateOf(Movie())


    private val _allMoviesFlow: Flow<PagingData<Movie>> = Pager(PagingConfig(pageSize = 30)) { pagingSource }.flow.cachedIn(viewModelScope)
    val allMoviesFlow: Flow<PagingData<Movie>> = _allMoviesFlow

    init {
        Timber.d("MovieListViewModel initialized")
        viewModelScope.launch {

            //Non-Paged movieds
            /*runCatching {
                repo.getMoviesFromNetwork()
            }.onFailure { error: Throwable ->
                Timber.d("fetching movies failed, $error")
            }.onSuccess { listOfMovies ->
                Timber.d("fetching movies successful")
                _listOfMovies.value = listOfMovies
            }*/

            //Paged movies
            _allMoviesFlow.collect { it ->
                it.map {
                    _listOfMovies.value.add(it)
                }
            }
        }
    }




    //Occurs on movie selection
    fun getMovieDetails(){
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