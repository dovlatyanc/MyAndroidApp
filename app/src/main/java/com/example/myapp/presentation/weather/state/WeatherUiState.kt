package com.example.myapp.presentation.weather.state

import com.example.myapp.domain.weather.model.WeatherForecast

sealed class WeatherUiState {
    object Loading : WeatherUiState()
    data class Success(val forecasts: List<WeatherForecast>) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
}