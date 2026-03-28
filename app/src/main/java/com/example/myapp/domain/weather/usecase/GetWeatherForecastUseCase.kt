package com.example.myapp.domain.weather.usecase

import com.example.myapp.domain.weather.model.WeatherForecast
import com.example.myapp.domain.weather.repository.WeatherRepository

class GetWeatherForecastUseCase(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(city: String): Result<List<WeatherForecast>> {
        return repository.getWeatherForecast(city)
    }
}