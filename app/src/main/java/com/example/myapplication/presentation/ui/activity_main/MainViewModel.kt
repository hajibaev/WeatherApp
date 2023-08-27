package com.example.myweatherapp.presentation.ui.activity_main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweatherapp.domain.use_case.WeatherUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel(private val weatherUseCase: WeatherUseCase) : ViewModel() {

    private val _cityFlow = MutableStateFlow("")
    val cityFlow get() = _cityFlow.asStateFlow()

    val weather = _cityFlow.flatMapLatest {
        weatherUseCase.invoke(it)
    }.flowOn(Dispatchers.Default).catch { exception: Throwable -> }

    fun emitCity(city: String) = viewModelScope.launch {
        _cityFlow.tryEmit(city)
        _cityFlow.value = city
    }

}