package com.example.myapp.domain.model
import java.io.Serializable

data class WeatherForecast(
    val date: String,
    val dateTime: String,
    val temperature: Double,
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    val description: String,
    val iconCode: String,
    val humidity: Int,
    val pressure: Int,
    val windSpeed: Double,
    val city: String,
    val country: String,
    val timezone: Int
): Serializable