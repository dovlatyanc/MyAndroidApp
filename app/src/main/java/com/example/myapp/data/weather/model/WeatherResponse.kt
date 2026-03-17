package com.example.myapp.data.weather.model


data class WeatherResponse(
    val city: City,
    val list: List<WeatherItem>
)