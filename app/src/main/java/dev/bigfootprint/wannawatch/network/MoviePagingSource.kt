package dev.bigfootprint.wannawatch.network

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.bigfootprint.wannawatch.model.Movie
import dev.bigfootprint.wannawatch.util.MovieDtoMapper
import timber.log.Timber
import javax.inject.Inject

class MoviePagingSource(): PagingSource<Int, Movie>() {


    init {
        Timber.d("MovingPagingSource initialized")
    }

    @Inject
    lateinit var mapper: MovieDtoMapper

    @Inject
    lateinit var api: TMDBApiService

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key?:1
        return try {
            val jsonMovieList = api.getMovies(page)
            Timber.d("json retrieved: $jsonMovieList")
            val convertedList = mapper.convertJsonToMovieObject(jsonMovieList)
            Timber.d("converted list: $convertedList")
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


    @ExperimentalPagingApi
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }


}