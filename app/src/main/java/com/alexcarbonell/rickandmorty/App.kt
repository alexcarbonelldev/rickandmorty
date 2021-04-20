package com.alexcarbonell.rickandmorty

import android.app.Application
import com.alexcarbonell.rickandmorty.di.appModule
import com.alexcarbonell.rickandmorty.di.dataModule
import com.alexcarbonell.rickandmorty.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                appModule,
                domainModule,
                dataModule
            )
        }
    }
}
