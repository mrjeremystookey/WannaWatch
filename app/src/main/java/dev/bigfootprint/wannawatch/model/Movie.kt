package dev.bigfootprint.wannawatch.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Movie(
            @Json(name = "id")
            var movieId: String? = null,
            @Json(name = "original_title")
            var title: String? = null,
            @Json(name = "poster_path")
            var moviePoster: String? = null,
            @Json(name = "backdrop_path")
            var backDrop: String? = null,
            @Json(name = "overview")
            var description: String? = null,
            @Json(name = "vote_average")
            var voteAverage: String? = null,
            @Json(name = "revenue")
            var revenue: String? = null,
            @Json(name = "release_date")
            var releaseDate: String? = null,
            var genres: List<String>? = emptyList()
            ) {

}