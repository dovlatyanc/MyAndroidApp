package com.example.myapp.core.di.weather

import com.example.myapp.data.repository.WeatherRepositoryImpl
import com.example.myapp.data.weather.remote.RetrofitClient
import com.example.myapp.data.weather.remote.WeatherApi
import com.example.myapp.data.weather.remote.WeatherRemoteDataSource

import com.example.myapp.domain.weather.repository.WeatherRepository

import org.koin.dsl.module

val dataModule = module {

    single { RetrofitClient }

    single<WeatherApi> {
        get<RetrofitClient>().api
    }

    single {
        WeatherRemoteDataSource()
    }

    single<WeatherRepository> {
        WeatherRepositoryImpl(
            remoteDataSource = get()
        )
    }
}