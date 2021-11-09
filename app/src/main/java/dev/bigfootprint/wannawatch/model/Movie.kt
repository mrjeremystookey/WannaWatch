package dev.bigfootprint.wannawatch.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Movie(
            @Json(name = "original_title")
            var title: String,
            @Json(name = "poster_path")
            var moviePoster: String,
            @Json(name = "overview")
            var description: String?,
            @Json(name = "vote_average")
            var voteAverage: String?,
            @Json(name = "revenue")
            var revenue: String?,
            @Json(name = "release_date")
            var releaseDate: String?) {

}