package de.mindmarket.navigateviaviewmodelsample

import android.app.Application
import de.mindmarket.navigateviaviewmodelsample.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * This is needed to initialize Koin.
 */
class MyApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            androidLogger()
            modules(appModule)
        }
    }
}