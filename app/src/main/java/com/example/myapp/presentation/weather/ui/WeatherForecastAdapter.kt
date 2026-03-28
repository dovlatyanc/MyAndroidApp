package com.example.myapp.presentation.weather.ui

import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapp.R
import com.example.myapp.databinding.ItemWeatherForecastBinding
import com.example.myapp.domain.weather.model.WeatherForecast
import com.example.myapp.presentation.weather.WeatherStyleHelper

class WeatherForecastAdapter(
    private var forecasts: List<WeatherForecast> = emptyList(),
    private val onItemClick: (WeatherForecast) -> Unit
) : RecyclerView.Adapter<WeatherForecastAdapter.WeatherViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newForecasts: List<WeatherForecast>) {
        forecasts = newForecasts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = ItemWeatherForecastBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(forecasts[position])
        holder.binding.root.setOnClickListener {
            onItemClick(forecasts[position])
        }
    }

    override fun getItemCount(): Int = forecasts.size

    class WeatherViewHolder(
        val binding: ItemWeatherForecastBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(forecast: WeatherForecast) {
            // Градиент фона
            val (startColor, endColor) = WeatherStyleHelper.getBackgroundColors(forecast.description)
            val gradient = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                intArrayOf(
                    ContextCompat.getColor(binding.root.context, startColor),
                    ContextCompat.getColor(binding.root.context, endColor)
                )
            )
            gradient.cornerRadius = binding.root.context.resources.getDimension(R.dimen.corner_radius)
            binding.cardBackground.background = gradient


            val iconUrl = WeatherStyleHelper.getWeatherIconUrl(forecast.iconCode)
            Glide.with(binding.imageWeatherIcon.context)
                .load(iconUrl)
                .placeholder(android.R.drawable.ic_menu_report_image)
                .into(binding.imageWeatherIcon)

            binding.textDate.text = forecast.date.substring(5)
            binding.textTemperature.text = WeatherStyleHelper.formatTemperature(forecast.temperature)
            binding.textDescription.text = forecast.description.replaceFirstChar { it.uppercase() }
            binding.textExtra.text = "💧 ${forecast.humidity}%  •  🌬 ${forecast.windSpeed} м/с"
        }
    }
}