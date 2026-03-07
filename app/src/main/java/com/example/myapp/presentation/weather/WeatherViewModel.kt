package com.example.myapp.presentation.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.domain.model.WeatherForecast
import com.example.myapp.domain.useCase.GetWeatherForecastUseCase
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val getWeatherForecastUseCase: GetWeatherForecastUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<WeatherUiState>()
    val uiState: LiveData<WeatherUiState> = _uiState

    fun loadWeather(city: String) {
        viewModelScope.launch {
            _uiState.value = WeatherUiState.Loading

            getWeatherForecastUseCase(city)
                .onSuccess { forecasts ->
                    _uiState.value = WeatherUiState.Success(forecasts)
                }
                .onFailure { error ->
                    _uiState.value = WeatherUiState.Error(error.message ?: "Unknown error")
                }
        }
    }
}