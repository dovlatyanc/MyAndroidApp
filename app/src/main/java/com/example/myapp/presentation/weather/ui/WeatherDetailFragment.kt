package com.example.myapp.presentation.weather.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.myapp.R
import com.example.myapp.databinding.FragmentWeatherDetailBinding
import com.example.myapp.domain.weather.model.WeatherForecast
import com.example.myapp.presentation.weather.WeatherStyleHelper

class WeatherDetailFragment : Fragment(R.layout.fragment_weather_detail) {

    private val args: WeatherDetailFragmentArgs by navArgs()

    private var _binding: FragmentWeatherDetailBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWeatherDetailBinding.bind(view)


        binding.btnRetry.text = getString(R.string.weather_retry)

        binding.swipeRefresh.setOnRefreshListener {
            args.forecast?.let { showDetails(it) }
            binding.swipeRefresh.isRefreshing = false
        }

        args.forecast?.let {
            showDetails(it)
        } ?: showErrorState()
    }

    @SuppressLint("SetTextI18n")
    private fun showDetails(forecast: WeatherForecast) {
        binding.btnRetry.visibility = View.GONE

        val (startColor, endColor) = WeatherStyleHelper.getBackgroundColors(forecast.description)
        binding.root.setBackgroundColor(
            ContextCompat.getColor(requireContext(), startColor)
        )

        binding.textCity.text = "${forecast.city}, ${forecast.country}"
        binding.textDateTime.text = forecast.dateTime
        binding.textTemperature.text = "${forecast.temperature}°C"


        binding.textFeelsLike.text = getString(R.string.weather_feels_like, forecast.feelsLike)
        binding.textRange.text = getString(R.string.weather_temp_min_max, forecast.tempMin, forecast.tempMax)
        binding.textDescription.text = forecast.description.replaceFirstChar { it.uppercase() }
        binding.textHumidity.text = getString(R.string.weather_humidity, forecast.humidity)
        binding.textPressure.text = getString(R.string.weather_pressure, forecast.pressure)
        binding.textWind.text = getString(R.string.weather_wind, forecast.windSpeed)

        val iconUrl = WeatherStyleHelper.getWeatherIconUrl(forecast.iconCode)
        Glide.with(this)
            .load(iconUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .into(binding.imageWeatherIcon)
    }

    private fun showErrorState() {
        binding.textCity.text = getString(R.string.weather_error_title)
        binding.textTemperature.text = getString(R.string.weather_error_temp)
        binding.textDescription.text = getString(R.string.weather_error_description)

        binding.btnRetry.visibility = View.VISIBLE
        binding.btnRetry.setOnClickListener {
            args.forecast?.let {
                showDetails(it)
                binding.btnRetry.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}