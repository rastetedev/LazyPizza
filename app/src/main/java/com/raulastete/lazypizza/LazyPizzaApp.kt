package com.raulastete.lazypizza

import android.app.Application
import com.raulastete.lazypizza.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class LazyPizzaApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@LazyPizzaApp)
            modules(appModule)
        }
    }
}