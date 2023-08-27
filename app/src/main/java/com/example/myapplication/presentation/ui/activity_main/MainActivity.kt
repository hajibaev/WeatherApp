package com.example.myweatherapp.presentation.ui.activity_main

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myweatherapp.domain.models.ForecastDay
import com.example.myweatherapp.presentation.adapter.WeatherAdapter
import com.example.myweatherapp.presentation.utils.hide
import com.example.myweatherapp.presentation.utils.observeDate
import com.example.myweatherapp.presentation.utils.show
import com.example.myweatherapp.presentation.utils.showRoundedImage
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale


class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val adapter by lazy {
        WeatherAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        requestPermission()
        observeWeather()
        binding.mainRecyclerview.adapter = adapter
    }

    private fun observeWeather() = with(viewModel) {
        cityFlow.onEach {
            if (cityFlow.value != "") {
                lifecycleScope.launchWhenStarted {
                    viewModel.weather.collectLatest {
                        adapter.setData(it.forecast.forecastday)
                        initViews(it.forecast.forecastday)
                    }
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun initViews(weather: List<ForecastDay>) = with(binding.mainScreen) {
        val firstObject: ForecastDay? = weather.getOrNull(0)
        firstObject?.apply {
            binding.mainScreen.date.text = observeDate(date)
            description.text = day.condition.text
            temperature.text = "Average temperature ${day.avgTemp} °C"
            imageViewIcon.showRoundedImage(imageUrl = "https:${day.condition.iconUrl}")
            screenVisibility()
        }
    }


    private fun screenVisibility() = with(binding) {
        isEmptyLoading.hide()
        isEmptyLoading.pauseAnimation()
        mainScreen.root.show()
        mainCardView.show()
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            ACCESS_LOCATION_PERMISSION_ID -> {
                if (ActivityCompat.checkSelfPermission(
                        this, Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this, Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermission()
                } else {
                    val geocoder = Geocoder(this, Locale.ENGLISH) // Используем английскую локаль
                    val mLocationRequest: LocationRequest = LocationRequest.create()
                    mLocationRequest.interval = 600
                    mLocationRequest.fastestInterval = 500
                    mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                    val mLocationCallback: LocationCallback = object : LocationCallback() {
                        override fun onLocationResult(locationResult: LocationResult) {
                            for (location in locationResult.locations) {
                                location?.let {
                                    val fromLocation = geocoder.getFromLocation(
                                        it.latitude, it.longitude, 1
                                    )
                                    if (!fromLocation.isNullOrEmpty()) {
                                        fromLocation[0].locality?.let {
                                            binding.mainScreen.location.text = it
                                            viewModel.emitCity(it)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    LocationServices.getFusedLocationProviderClient(this)
                        .requestLocationUpdates(mLocationRequest, mLocationCallback, null)
                }
            }
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            ), ACCESS_LOCATION_PERMISSION_ID
        )
    }

    companion object {
        private const val ACCESS_LOCATION_PERMISSION_ID = 1
    }
}