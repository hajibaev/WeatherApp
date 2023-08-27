package com.example.myweatherapp.presentation.di

import com.example.myweatherapp.data.repository.WeatherRepositoryImpl
import com.example.myweatherapp.domain.repository.WeatherRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<WeatherRepository> { WeatherRepositoryImpl(service = get()) }

}