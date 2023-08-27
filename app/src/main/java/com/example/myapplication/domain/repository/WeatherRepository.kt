package com.example.myweatherapp.domain.repository

import com.example.myweatherapp.domain.models.WeatherResponseModel
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun fetchAllWeather(city: String): Flow<WeatherResponseModel>

}