package com.example.myapp.presentation.weather.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.myapp.R
import com.example.myapp.databinding.ActivityWeatherDetailBinding
import com.example.myapp.data.repository.WeatherRepositoryImpl
import com.example.myapp.data.weather.remote.WeatherRemoteDataSource
import com.example.myapp.domain.weather.model.WeatherForecast
import com.example.myapp.domain.weather.usecase.GetWeatherForecastUseCase
import com.example.myapp.presentation.weather.WeatherStyleHelper
import com.example.myapp.presentation.weather.viewmodel.WeatherViewModel
import com.example.myapp.presentation.weather.state.WeatherUiState

class WeatherDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeatherDetailBinding
    private lateinit var weatherViewModel: WeatherViewModel

    private var currentForecast: WeatherForecast? = null
    private var cityName: String = "Moscow"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWeatherDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = getString(R.string.weather_detail_title)
        }

        val repository = WeatherRepositoryImpl(WeatherRemoteDataSource())
        val useCase = GetWeatherForecastUseCase(repository)
        weatherViewModel = WeatherViewModel(useCase)

        binding.swipeRefresh.setOnRefreshListener {
            refreshDataFromApi()
        }

        currentForecast = intent.getSerializableExtra("EXTRA_FORECAST") as? WeatherForecast

        if (currentForecast != null) {
            cityName = currentForecast!!.city
            showDetails(currentForecast!!)
        } else {
            showErrorState()
        }

        weatherViewModel.uiState.observe(this) { state ->
            when (state) {
                is WeatherUiState.Loading -> {
                }
                is WeatherUiState.Success -> {
                    state.forecasts.firstOrNull()?.let { newForecast ->
                        currentForecast = newForecast
                        showDetails(newForecast)
                    }
                    binding.swipeRefresh.isRefreshing = false
                }
                is WeatherUiState.Error -> {
                    Toast.makeText(
                        this,
                        "Не удалось обновить: ${state.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.swipeRefresh.isRefreshing = false
                }
            }
        }
    }

    private fun refreshDataFromApi() {
        weatherViewModel.loadWeather(cityName)
    }

    private fun showErrorState() {
        binding.textCity.text = "Ошибка загрузки"
        binding.textTemperature.text = "--"
        binding.textDescription.text = "Не удалось получить данные о погоде"

        binding.btnRetry.visibility = View.VISIBLE
        binding.btnRetry.setOnClickListener {
            val forecast = intent.getSerializableExtra("EXTRA_FORECAST") as? WeatherForecast
            if (forecast != null) {
                currentForecast = forecast
                cityName = forecast.city
                showDetails(forecast)
                binding.btnRetry.visibility = View.GONE
            } else {

                refreshDataFromApi()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showDetails(forecast: WeatherForecast) {

        binding.btnRetry.visibility = View.GONE

        val (startColor, endColor) = WeatherStyleHelper.getBackgroundColors(forecast.description)
        binding.root.setBackgroundColor(
            ContextCompat.getColor(this, startColor)
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
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .into(binding.imageWeatherIcon)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}