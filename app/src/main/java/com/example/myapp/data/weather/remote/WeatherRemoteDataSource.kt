package com.example.myapp.data.weather.remote

import com.example.myapp.data.weather.model.WeatherResponse

class WeatherRemoteDataSource {
    private val api = RetrofitClient.api

    suspend fun getForecast(city: String): WeatherResponse {
        return api.getForecast(city = city, apiKey = RetrofitClient.getApi())
    }
}