package dev.bigfootprint.wannawatch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.bigfootprint.wannawatch.viewmodels.MovieViewModel
import timber.log.Timber

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private val movieViewModel: MovieViewModel by activityViewModels()

    override fun onStart() {
        Timber.d("onStart called")
        super.onStart()
    }

    override fun onPause() {
        Timber.d("onPause called")
        super.onPause()
    }

    override fun onDestroy() {
        Timber.d("onDestroy called")
        super.onDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            Timber.d("onCreateView called")
            movieViewModel.selectedMovie.value.movieId?.let { movieViewModel.getMovieDetails(it) }
            setContent {
                Text("${movieViewModel.selectedMovieJson.value}", color= Color.White)
            }
        }
    }


}