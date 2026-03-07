package com.example.myapp.domain.repository

import com.example.myapp.domain.model.WeatherForecast

interface WeatherRepository {
    suspend fun getWeatherForecast(city: String): Result<List<WeatherForecast>>
}