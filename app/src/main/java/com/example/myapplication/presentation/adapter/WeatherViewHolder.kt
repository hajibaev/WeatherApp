package com.example.myweatherapp.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemDayBinding
import com.example.myweatherapp.domain.models.ForecastDay
import com.example.myweatherapp.presentation.utils.inflate
import com.example.myweatherapp.presentation.utils.observeDate
import com.example.myweatherapp.presentation.utils.showRoundedImage

class WeatherViewHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(parent.inflate(R.layout.item_day)) {

    private val binding = ItemDayBinding.bind(itemView)

    fun bind(weather: ForecastDay) = with(binding) {
        weather.apply {
            val temp = "Average temperature ${day.avgTemp} °C"
            val maxTemp = "Max wind ${day.maxWind}"
            val avgHumidity = "Average humidity ${day.avgHumidity} %"
            textDate.text = observeDate(date)
            textDescription.text = day.condition.text
            textTemperature.text = temp
            textMaxWind.text = maxTemp
            textAvgHumidity.text = avgHumidity
            imageIcon.showRoundedImage(imageUrl = "https:${day.condition.iconUrl}")
        }
    }
}