package com.example.myweatherapp.presentation.di

import com.example.myweatherapp.presentation.ui.activity_main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModel = module {

    viewModel { MainViewModel(weatherUseCase = get()) }

}