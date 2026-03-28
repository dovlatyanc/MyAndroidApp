package com.example.myapp.core.di.calculator

import com.example.myapp.data.calculator.service.ExpressionCalculator
import org.koin.dsl.module

val calculatorDataModule = module {

    single {
        ExpressionCalculator()
    }
}