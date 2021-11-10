package dev.bigfootprint.wannawatch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
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

                Column(Modifier.fillMaxSize()) {
                    //Header image backdrop path
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .weight(.1f)){
                        Image(
                            painter = rememberImagePainter(
                                data = "https://www.themoviedb.org/t/p/w1280"+ movieViewModel.selectedMovie.value.backDrop,
                                builder = {}),
                            contentDescription = "backdrop",
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                    LazyColumn(modifier = Modifier.weight(.2f),content ={
                        item {
                            Row(Modifier.weight(1f)){
                                Image(
                                    painter = rememberImagePainter(
                                        data = "https://www.themoviedb.org/t/p/w1280"+ movieViewModel.selectedMovie.value.moviePoster,
                                        builder = {}),
                                    contentDescription = "Movie Poster",
                                    modifier = Modifier
                                        .size(256.dp)
                                        .padding(32.dp)
                                        .weight(.8f)
                                        .fillMaxSize(),
                                )
                                Column(Modifier.weight(.5f).padding(16.dp)) {
                                    Text("${movieViewModel.selectedMovie.value.title}", color = Color.White, fontSize = 24.sp, textAlign = TextAlign.Left)
                                    Divider(thickness = 3.dp, color = Color.Yellow)
                                    Text("${movieViewModel.selectedMovie.value.description}", color = Color.White)
                                }
                            }
                        }
                        item {
                            Text(modifier = Modifier, text = "Revenue (USD): $${movieViewModel.selectedMovie.value.revenue}", color= Color.White)
                            Text(modifier = Modifier, text = "Genres: ${movieViewModel.selectedMovie.value.genres.toString()}", color= Color.White)
                            Text(modifier = Modifier, text = "Release Date:  ${movieViewModel.selectedMovie.value.releaseDate}", color= Color.White)
                            Text(modifier = Modifier, text = "Average Score:  ${movieViewModel.selectedMovie.value.voteAverage}", color= Color.White)

                        }
                    })

                }

            }
        }
    }


}