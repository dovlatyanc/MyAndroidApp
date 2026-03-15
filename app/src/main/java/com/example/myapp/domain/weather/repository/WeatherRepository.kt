package com.example.myapp.domain.weather.repository

import com.example.myapp.domain.weather.model.WeatherForecast

interface WeatherRepository {
    suspend fun getWeatherForecast(city: String): Result<List<WeatherForecast>>
}