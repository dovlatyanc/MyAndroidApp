package com.example.myapp.presentation.weather.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.databinding.ActivityWeatherBinding
import com.example.myapp.presentation.weather.state.WeatherUiState
import com.example.myapp.presentation.weather.viewmodel.WeatherViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeatherBinding

    private val weatherViewModel: WeatherViewModel by viewModel()

    private lateinit var weatherAdapter: WeatherForecastAdapter

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }


        weatherAdapter = WeatherForecastAdapter(
            onItemClick = { forecast ->
                val intent = Intent(this, WeatherDetailActivity::class.java)
                intent.putExtra("EXTRA_FORECAST", forecast)
                startActivity(intent)
            }
        )

        binding.recyclerViewWeather.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewWeather.adapter = weatherAdapter


        weatherViewModel.uiState.observe(this) { state ->
            when (state) {
                is WeatherUiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerViewWeather.visibility = View.GONE
                }
                is WeatherUiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerViewWeather.visibility = View.VISIBLE

                    if (state.forecasts.isNotEmpty()) {
                        binding.textCity.text = "${state.forecasts[0].city}, ${state.forecasts[0].country}"
                    }

                    weatherAdapter.submitList(state.forecasts)
                }
                is WeatherUiState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerViewWeather.visibility = View.GONE
                    Toast.makeText(
                        this,
                        "Ошибка: ${state.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }


        weatherViewModel.loadWeather("Moscow")
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}