package com.example.myweatherapp.domain.models

import com.google.gson.annotations.SerializedName

data class WeatherResponseModel(
    val current: Current,
    val forecast: Forecast,
)


data class Current(
    @SerializedName("temp_c") val temp: Float,
    @SerializedName("condition") val condition: Condition,
)

data class Forecast(
    @SerializedName("forecastday") val forecastday: List<ForecastDay>,
)

data class ForecastDay(
    @SerializedName("date") val date: String,
    @SerializedName("day") val day: Day,
)

/**
 * Данные на день
 *
 * @param condition Погодные условия
 * @param avgTemp Средняя температура
 * @param maxWind Максимальная скорость ветра
 * @param avgHumidity Средняя влажность воздуха
 */
data class Day(
    @SerializedName("condition") val condition: Condition,
    @SerializedName("avgtemp_c") val avgTemp: Float,
    @SerializedName("maxwind_kph") val maxWind: Float,
    @SerializedName("avghumidity") val avgHumidity: Float,
)

/**
 * Погодные условия
 *
 * @param text Текстовое описание
 * @param iconUrl Url для графического отображения
 */
data class Condition(
    @SerializedName("text") val text: String,
    @SerializedName("icon") val iconUrl: String,
)

