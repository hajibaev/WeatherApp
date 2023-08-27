package com.example.myweatherapp.data.service

import com.example.myweatherapp.domain.models.WeatherResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("forecast.json")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("days") day: Int = 5,
        @Query("key") key: String = "d82d0c7d5e524a27ba7163831231408"
    ): Response<WeatherResponseModel>

}