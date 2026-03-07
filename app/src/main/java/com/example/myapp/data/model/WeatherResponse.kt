package com.example.myapp.data.model


data class WeatherResponse(
    val city: City,
    val list: List<WeatherItem>
)