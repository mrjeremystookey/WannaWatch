package dev.bigfootprint.wannawatch.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
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
        return ComposeView(requireContext()).apply{
            Timber.d("OnCreateView called")
            setContent{
                LazyColumn(content = {
                    items(items = movieViewModel.listOfMovies.value){ movie ->
                        MovieCard(
                            movie = movie,
                            navigateToMovieDetails = {
                                val action = MovieListFragmentDirections.viewMovie()
                                Timber.d("navigating to movie: ${movie.title}")
                                findNavController().navigate(action)
                            }
                        )
                    }
                })
            }
        }
    }


}