package com.niksatyr.instectus

import android.app.Application
import com.niksatyr.instectus.di.networkModule
import com.niksatyr.instectus.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@App)
            modules(
                networkModule,
                repositoryModule
            )
        }
    }

}