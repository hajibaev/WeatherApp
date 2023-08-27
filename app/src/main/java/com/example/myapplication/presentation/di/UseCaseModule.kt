package com.example.myweatherapp.presentation.di

import com.example.myweatherapp.domain.use_case.WeatherUseCase
import com.example.myweatherapp.domain.use_case.WeatherUseCaseImpl
import org.koin.dsl.module

    val interactorModule = module {

    factory<WeatherUseCase> {
        WeatherUseCaseImpl(repository = get())
    }
}