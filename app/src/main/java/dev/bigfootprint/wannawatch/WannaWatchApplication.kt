package dev.bigfootprint.wannawatch

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class WannaWatchApplication: Application() {

    override fun onCreate() {
        Timber.plant(Timber.DebugTree())
        Timber.d("Timber tree planted")
        Timber.d("WannaWatchApplication onCreate called")
        super.onCreate()
    }
}