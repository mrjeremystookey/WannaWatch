package dev.bigfootprint.wannawatch.ui

import android.app.ActionBar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.compose.rememberImagePainter
import dagger.hilt.android.AndroidEntryPoint
import dev.bigfootprint.wannawatch.MainActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        val action: ActionBar? = requireActivity().actionBar
        action?.setDisplayHomeAsUpEnabled(true)

        super.onCreate(savedInstanceState)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                activity?.onBackPressed()
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val activity = activity as? MainActivity
        activity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)




        return ComposeView(requireContext()).apply {
            Timber.d("onCreateView called")
            movieViewModel.selectedMovie.value.movieId?.let { movieViewModel.getMovieDetails() }
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
                                        .size(512.dp)
                                        .weight(.8f)
                                        .fillMaxSize()
                                        .padding(16.dp),
                                )
                                Column(
                                    Modifier
                                        .weight(.5f)
                                        .padding(16.dp)) {
                                    Text("${movieViewModel.selectedMovie.value.title}", color = Color.White, fontSize = 24.sp, textAlign = TextAlign.Left)
                                    Divider(thickness = 3.dp, color = Color.Yellow)
                                    Text("${movieViewModel.selectedMovie.value.description}", color = Color.White, modifier = Modifier.padding(top = 16.dp))
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