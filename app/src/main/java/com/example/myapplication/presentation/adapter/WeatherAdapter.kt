package com.example.myweatherapp.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myweatherapp.domain.models.ForecastDay
import com.example.myweatherapp.presentation.utils.startSlideInRightAnim

class WeatherAdapter : RecyclerView.Adapter<WeatherViewHolder>() {

    private var data: List<ForecastDay> = emptyList()

    fun setData(data: List<ForecastDay>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder(parent)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.startSlideInRightAnim()
    }

    override fun getItemCount(): Int = data.size
}
