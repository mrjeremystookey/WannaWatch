package dev.bigfootprint.wannawatch.util

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import dev.bigfootprint.wannawatch.model.Movie
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

class MovieDtoMapper @Inject constructor(moshi: Moshi) {

    private var adapter: JsonAdapter<Movie>

    init {
        Timber.d("PlanetMapper initialized")
        adapter = moshi.adapter(Movie::class.java)
    }


    fun convertJsonToMovieObject(allPopularMoviesJson: JSONObject): MutableList<Movie> {
        Timber.d("converting json to list of Movies")
        val movieList = mutableListOf<Movie>()
        //Parse JSON OBJECT
        val allMoviesOnPage = allPopularMoviesJson.getJSONArray("results")
        for(i in 0 until allMoviesOnPage.length()){
            //Timber.d("${allMoviesOnPage[i]}")
            val convertedMovie = adapter.fromJson(allMoviesOnPage[i].toString())
            Timber.d("Movie Title: ${convertedMovie?.title}")
            movieList.add(convertedMovie!!)
        }
        return movieList
    }

    fun parseMovieDetailsJson(movieDetailsJson: JSONObject){
        Timber.d("converting movie details json to Planet")

    }


}