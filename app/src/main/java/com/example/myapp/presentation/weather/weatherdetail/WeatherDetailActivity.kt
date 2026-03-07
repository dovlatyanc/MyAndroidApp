package com.example.myapp.presentation.weather.weatherdetail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.myapp.databinding.ActivityWeatherDetailBinding
import com.example.myapp.domain.model.WeatherForecast
import com.example.myapp.presentation.weather.WeatherStyleHelper

class WeatherDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeatherDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityWeatherDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = getString(com.example.myapp.R.string.weather_detail_title)
        }

        val forecast = intent.getSerializableExtra("EXTRA_FORECAST") as? WeatherForecast

        if (forecast != null) {
            showDetails(forecast)
        } else {
            // Если данные не пришли — показываем заглушку
            binding.textCity.text = "Ошибка загрузки"
            binding.textTemperature.text = "--"
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showDetails(forecast: WeatherForecast) {
        val (startColor, endColor) = WeatherStyleHelper.getBackgroundColors(forecast.description)

        binding.detailCard.setCardBackgroundColor(
            ContextCompat.getColor(this, com.example.myapp.R.color.weather_cloudy_start)
        )

        binding.textCity.text = "${forecast.city}, ${forecast.country}"
        binding.textDateTime.text = forecast.dateTime
        binding.textTemperature.text = "${forecast.temperature}°C"
        binding.textFeelsLike.text = "Ощущается как: ${forecast.feelsLike}°C"
        binding.textRange.text = "Мин/Макс: ${forecast.tempMin}°C / ${forecast.tempMax}°C"
        binding.textDescription.text = forecast.description.replaceFirstChar { it.uppercase() }
        binding.textHumidity.text = "Влажность: ${forecast.humidity}%"
        binding.textPressure.text = "Давление: ${forecast.pressure} гПа"
        binding.textWind.text = "Ветер: ${forecast.windSpeed} м/с"


        val iconUrl = WeatherStyleHelper.getWeatherIconUrl(forecast.iconCode)
        Glide.with(this)
            .load(iconUrl)
            .placeholder(com.example.myapp.R.drawable.ic_launcher_foreground) // заглушка, если есть
            .into(binding.imageWeatherIcon)
    }


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}