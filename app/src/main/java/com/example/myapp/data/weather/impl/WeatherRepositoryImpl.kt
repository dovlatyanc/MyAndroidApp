package com.example.myapp.data.weather.impl

import com.example.myapp.data.weather.remote.WeatherRemoteDataSource
import com.example.myapp.domain.weather.model.WeatherForecast
import com.example.myapp.domain.weather.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val remoteDataSource: WeatherRemoteDataSource
) : WeatherRepository {

    override suspend fun getWeatherForecast(city: String): Result<List<WeatherForecast>> {
        return try {
            val response = remoteDataSource.getForecast(
                city = city,
                cnt = 56 // Запрашиваем ровно 56 записей, чтобы получить 7 дневных прогнозов
            )

            // Берем каждую 8-ю запись (по одной на день)
            val forecast = response.list
                .filterIndexed { index, _ -> index % 8 == 0 }
                .map { item ->
                    WeatherForecast(
                        date = item.dataTxt.substring(0, 10),
                        dateTime = item.dataTxt,
                        temperature = item.main.temp,
                        feelsLike = item.main.feelsLike,
                        tempMin = item.main.tempMin,
                        tempMax = item.main.tempMax,
                        description = item.weather.firstOrNull()?.description ?: "N/A",
                        iconCode = item.weather.firstOrNull()?.icon ?: "01d",
                        humidity = item.main.humidity ?: 0,
                        pressure = item.main.pressure ?: 0,
                        windSpeed = item.wind?.speed ?: 0.0,
                        city = response.city.name,
                        country = response.city.country,
                        timezone = response.city.timezone
                    )
                }

            Result.success(forecast)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}