package dev.bigfootprint.wannawatch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import dagger.hilt.android.AndroidEntryPoint
import dev.bigfootprint.wannawatch.MainActivity
import dev.bigfootprint.wannawatch.ui.composables.MovieCard
import dev.bigfootprint.wannawatch.viewmodels.MovieViewModel
import timber.log.Timber

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private val movieViewModel: MovieViewModel by activityViewModels()

    override fun onPause() {
        Timber.d("onPause called")
        super.onPause()
    }

    override fun onStart() {
        Timber.d("onStart called")
        super.onStart()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val activity = activity as? MainActivity
        activity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(false)


        return ComposeView(requireContext()).apply{
            Timber.d("OnCreateView called")
            setContent{
                val lazyMovies = movieViewModel.allMoviesFlow.collectAsLazyPagingItems()
                //Loading Paging movies
                Timber.d("number of movies: ${lazyMovies.itemCount}")
                LazyColumn(){
                    items(lazyMovies){ movie ->
                        movie?.let {
                            MovieCard(
                                movie = movie,
                                navigateToMovieDetails = {
                                    val action = MovieListFragmentDirections.viewMovie()
                                    Timber.d("navigating to movie: ${movie.movieId}")
                                    movieViewModel.selectedMovie.value = movie
                                    findNavController().navigate(action)
                                }
                            )
                        }
                    }
                }
                lazyMovies.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            //You can add modifier to manage load state when first time response page is loading
                        }
                        loadState.append is LoadState.Loading -> {
                            //You can add modifier to manage load state when next response page is loading
                        }
                        loadState.append is LoadState.Error -> {
                            //You can use modifier to show error message
                        }
                    }
                }

                //Non-Paged movies
                /*LazyColumn(content = {
                    items(items = movieViewModel.listOfMovies.value){ movie ->
                        MovieCard(
                            movie = movie,
                            navigateToMovieDetails = {
                                val action = MovieListFragmentDirections.viewMovie()
                                Timber.d("navigating to movie: ${movie.movieId}")
                                movieViewModel.selectedMovie.value = movie
                                findNavController().navigate(action)
                            }
                        )
                    }
                }
                )*/
            }
        }
    }


}