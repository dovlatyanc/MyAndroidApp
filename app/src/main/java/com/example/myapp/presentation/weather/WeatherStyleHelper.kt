package com.example.myapp.presentation.weather
import com.example.myapp.R

object WeatherStyleHelper {

    fun getBackgroundColors(description: String): Pair<Int, Int> {
        val desc = description.lowercase()
        return when {
            desc.contains("rain") || desc.contains("drizzle") ->
                R.color.weather_rain_start to R.color.weather_rain_end
            desc.contains("snow") ->
                R.color.weather_snow_start to R.color.weather_snow_end
            desc.contains("thunder") || desc.contains("storm") ->
                R.color.weather_storm_start to R.color.weather_storm_end
            desc.contains("cloud") ->
                R.color.weather_cloudy_start to R.color.weather_cloudy_end
            desc.contains("clear") || desc.contains("sun") ->
                R.color.weather_sunny_start to R.color.weather_sunny_end
            else ->
                R.color.weather_cloudy_start to R.color.weather_cloudy_end
        }
    }

    fun getWeatherIconUrl(iconCode: String): String {
        return "https://openweathermap.org/img/wn/$iconCode@2x.png"
    }

    fun formatTemperature(temp: Double): String {
        return when {
            temp < -10 -> "❄️ ${temp}°C"
            temp < 0 -> "🌨 ${temp}°C"
            temp < 15 -> "🌤 ${temp}°C"
            temp < 25 -> "☀️ ${temp}°C"
            else -> "🔥 ${temp}°C"
        }
    }
}