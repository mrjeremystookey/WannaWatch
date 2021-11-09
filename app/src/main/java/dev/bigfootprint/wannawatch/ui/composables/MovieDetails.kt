package dev.bigfootprint.wannawatch.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.bigfootprint.wannawatch.model.Movie

@Composable
fun MovieDetails(movie: Movie) {
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colors.primary),
    ){

    }
}