package com.example.myapp.core

import android.app.Application
import com.example.myapp.core.di.calculator.calculatorDataModule
import com.example.myapp.core.di.calculator.calculatorDomainModule
import com.example.myapp.core.di.calculator.calculatorPresentationModule
import com.example.myapp.core.di.weather.dataModule
import com.example.myapp.core.di.weather.domainModule
import com.example.myapp.core.di.weather.weatherPresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()


        startKoin {
            androidContext(this@MyApp)
            androidLogger()



            modules(
                dataModule,
                domainModule,
                weatherPresentationModule,
                calculatorDataModule,
                calculatorDomainModule,
                calculatorPresentationModule,

            )
        }
    }
}