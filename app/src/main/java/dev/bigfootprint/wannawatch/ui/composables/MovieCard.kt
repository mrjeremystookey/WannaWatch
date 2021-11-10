package dev.bigfootprint.wannawatch.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import dev.bigfootprint.wannawatch.model.Movie


@Composable
fun MovieCard(
    movie: Movie?,
    navigateToMovieDetails: () -> Unit,
){
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(
                bottom = 6.dp,
                top = 6.dp,
                start = 16.dp,
                end = 16.dp
            )
            .fillMaxWidth()
            .clickable(onClick = navigateToMovieDetails),
        elevation = 8.dp) {
        Row(
            Modifier
                .wrapContentSize()
        ){
            Column(Modifier.wrapContentSize().align(CenterVertically)) {
                Image(
                    painter = rememberImagePainter(
                        data = "https://www.themoviedb.org/t/p/w1280"+ movie?.moviePoster,
                        builder = {transformations(CircleCropTransformation())}),
                    contentDescription = "Movie Poster",
                    modifier = Modifier.size(128.dp).fillMaxHeight().padding(4.dp)
                )
            }
            Column(
                Modifier
                    .fillMaxWidth(.9f)
                    .padding(start = 8.dp, bottom = 8.dp)
                    .align(CenterVertically),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start) {
                Row(Modifier){
                    movie?.title?.let { Text(it, Modifier.weight(.8f) ,fontSize = 16.sp) }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "${movie?.releaseDate}",
                        modifier = Modifier.weight(.7f).align(CenterVertically),
                        fontSize = 12.sp,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Rating: ${movie?.voteAverage}",
                        modifier = Modifier.weight(.7f).align(CenterVertically),
                        fontSize = 12.sp,
                    )
                }
                Row(
                    Modifier
                        .padding(top = 4.dp)
                        .wrapContentSize()){
                    Text("" + movie?.description, fontSize = 12.sp)
                }
            }
            Column(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(.8f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End) {
            }
        }
    }
}
