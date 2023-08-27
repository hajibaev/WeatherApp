package com.example.weathertestapp.presentation.ui

import android.app.Application
import com.example.myweatherapp.presentation.di.interactorModule
import com.example.myweatherapp.presentation.di.repositoryModule
import com.example.myweatherapp.presentation.di.retrofitModule
import com.example.myweatherapp.presentation.di.viewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        instance = this

        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    retrofitModule,
                    repositoryModule,
                    interactorModule,
                    viewModel
                )
            )
        }
    }

    companion object {
        lateinit var instance: App
            private set
    }
}