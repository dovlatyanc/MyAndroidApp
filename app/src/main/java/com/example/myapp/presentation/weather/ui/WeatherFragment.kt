package com.example.myapp.presentation.weather.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.R
import com.example.myapp.databinding.FragmentWeatherBinding
import com.example.myapp.presentation.weather.state.WeatherUiState
import com.example.myapp.presentation.weather.viewmodel.WeatherViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherFragment : Fragment(R.layout.fragment_weather) {

    private val args: WeatherFragmentArgs by navArgs()


    private val weatherViewModel: WeatherViewModel by viewModel()

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    private lateinit var weatherAdapter: WeatherForecastAdapter

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWeatherBinding.bind(view)



        weatherAdapter = WeatherForecastAdapter(
            onItemClick = { forecast ->

                val action = WeatherFragmentDirections
                    .actionWeatherToDetail(forecast)
                findNavController().navigate(action)
            }
        )

        binding.recyclerViewWeather.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewWeather.adapter = weatherAdapter


        weatherViewModel.uiState.observe(viewLifecycleOwner) { state ->
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
                        requireContext(),
                        getString(R.string.err_weather_ui, state.message),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }


        weatherViewModel.loadWeather(args.city)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}