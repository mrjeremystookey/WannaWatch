package dev.bigfootprint.wannawatch.di

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.bigfootprint.wannawatch.network.TMDBApiService
import dev.bigfootprint.wannawatch.util.MovieDtoMapper
import timber.log.Timber
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object Module {

    @Singleton
    @Provides
    fun providesQueue(@ApplicationContext appContext: Context): RequestQueue {
        Timber.d("queue injected")
        return Volley.newRequestQueue(appContext)
    }

    @Singleton
    @Provides
    fun providesApiService(requestQueue: RequestQueue): TMDBApiService {
        Timber.d("Api Service injected")
        return TMDBApiService(requestQueue)
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        Timber.d("Moshi injected")
        return Moshi.Builder().build()
    }


    @Singleton
    @Provides
    fun providesDomainDtoImpl(): MovieDtoMapper {
        Timber.d("PlanetDto injected")
        return MovieDtoMapper(provideMoshi())
    }


}