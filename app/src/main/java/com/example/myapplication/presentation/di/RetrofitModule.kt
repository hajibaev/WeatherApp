package com.example.myweatherapp.presentation.di

import com.example.myweatherapp.data.service.WeatherService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://api.weatherapi.com/v1/"

const val CONNECT_TIMEOUT_SECONDS = 30L

val retrofitModule = module {

    single {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    single {
        OkHttpClient.Builder().addInterceptor(get<HttpLoggingInterceptor>())
            .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS).build()
    }
    single {
        GsonConverterFactory.create()
    }


    single {
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(get<GsonConverterFactory>())
            .client(get<OkHttpClient>()).build()
    }

    factory {
        get<Retrofit>().create(WeatherService::class.java)
    }


}