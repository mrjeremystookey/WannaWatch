package dev.bigfootprint.wannawatch.network

import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class TMDBApiService @Inject constructor(private var queue: RequestQueue) {


    private val API_KEY = "3cd945502ab73e9f07f43e1b930ba623"


    init {
        Timber.d("TMDBApiService started")
    }

    suspend fun getMovies(page: Int) = suspendCoroutine<JSONObject> { cont ->
        val GET_MOVIES_URL = "https://api.themoviedb.org/3/movie/popular?api_key=${API_KEY}&language=en-US&page=${page}"
        val jsonArrayRequest = JsonObjectRequest(
            Request.Method.GET,
            GET_MOVIES_URL,
            null,
            { response ->
                Timber.d("movie fetch worked")
                cont.resume(response)
            },
            {
                Timber.d("${it.cause}")
            })
        jsonArrayRequest.retryPolicy = DefaultRetryPolicy(
            20 * 1000,
            5,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        Timber.d("request added to queue")
        queue.add(jsonArrayRequest)
    }


    suspend fun getMovieDetails() = suspendCoroutine<JSONArray> {

    }


}