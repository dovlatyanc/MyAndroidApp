@file:Suppress("DEPRECATION")

package com.example.myapp.core.di.weather

import com.example.myapp.presentation.weather.viewmodel.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val weatherPresentationModule = module {

    viewModel {
        WeatherViewModel(
            getWeatherForecastUseCase = get()
        )
    }
}

