package com.example.myapp.domain.useCase

import com.example.myapp.domain.model.WeatherForecast
import com.example.myapp.domain.repository.WeatherRepository

class GetWeatherForecastUseCase(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(city: String): Result<List<WeatherForecast>> {
        return repository.getWeatherForecast(city)
    }
}