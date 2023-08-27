package com.example.myweatherapp.data.repository

import com.example.myweatherapp.domain.models.WeatherResponseModel
import com.example.myweatherapp.domain.repository.WeatherRepository
import com.example.myweatherapp.data.service.WeatherService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class WeatherRepositoryImpl(private val service: WeatherService) : WeatherRepository {

    override fun fetchAllWeather(city: String): Flow<WeatherResponseModel> = flow {
        emit(service.getWeather(city = city))
    }.flowOn(Dispatchers.IO).map { it.body()!! }
}