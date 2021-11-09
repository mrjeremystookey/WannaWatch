package dev.bigfootprint.wannawatch.network

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.bigfootprint.wannawatch.model.Movie
import dev.bigfootprint.wannawatch.util.MovieDtoMapper
import javax.inject.Inject

class MoviePagingSource(val api: TMDBApiService, val query: String): PagingSource<Int, Movie>() {

    @Inject
    lateinit var mapper: MovieDtoMapper

    @ExperimentalPagingApi
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return super.getRefreshKey(state)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key?:1
        return try {
            val jsonMovieList = api.getMovies(page)
            val convertedList = mapper.convertJsonToMovieObject(jsonMovieList)
            //Convert to Movie Object
            LoadResult.Page(
                data = convertedList,
                prevKey = page - 1,
                nextKey = page + 1
            )
        }
         catch (e: Exception){
            return LoadResult.Error(e)
        }
    }




}