package com.example.myweatherapp.domain.use_case

import com.example.myweatherapp.domain.models.WeatherResponseModel
import com.example.myweatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow

interface WeatherUseCase {
    fun invoke(city: String): Flow<WeatherResponseModel>
}

class WeatherUseCaseImpl(private val repository: WeatherRepository) : WeatherUseCase {

    override fun invoke(city: String): Flow<WeatherResponseModel> = repository.fetchAllWeather(city)

}