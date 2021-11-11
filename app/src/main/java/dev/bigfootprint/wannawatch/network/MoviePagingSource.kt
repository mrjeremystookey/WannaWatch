package dev.bigfootprint.wannawatch.network

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.android.volley.RequestQueue
import dev.bigfootprint.wannawatch.model.Movie
import dev.bigfootprint.wannawatch.util.MovieDtoMapper
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MoviePagingSource @Inject constructor(var mapper: MovieDtoMapper, var api: TMDBApiService): PagingSource<Int, Movie>() {


    init {
        Timber.d("MovingPagingSource initialized")
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        Timber.d("load called")
        val page = params.key?:1
        val jsonMovieList = api.getMovies(page)
        Timber.d("json retrieved: $jsonMovieList")
        val convertedList = mapper.convertJsonToMovieObject(jsonMovieList)
        Timber.d("converted list: $convertedList")

        return try {
            LoadResult.Page(
                data = convertedList,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (convertedList.isEmpty()) null else page + 1
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