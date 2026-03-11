package com.example.myapp.core.di.weather

import com.example.myapp.domain.weather.usecase.GetWeatherForecastUseCase
import org.koin.dsl.module

val domainModule = module {


    single {
        GetWeatherForecastUseCase(
            repository = get()
        )
    }
}