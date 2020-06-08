package com.niksatyr.invectus

import android.app.Application
import com.niksatyr.invectus.di.networkModule
import com.niksatyr.invectus.di.repositoryModule
import com.niksatyr.invectus.screen.details.detailsModule
import com.niksatyr.invectus.screen.main.mainModule
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
                repositoryModule,
                mainModule,
                detailsModule
            )
        }
    }

}