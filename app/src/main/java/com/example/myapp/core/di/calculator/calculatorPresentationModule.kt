package com.example.myapp.core.di.calculator

import com.example.myapp.presentation.calculator.viewmodel.CalculatorViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val calculatorPresentationModule = module {

    viewModel {
        CalculatorViewModel(
            calculator = get()
        )
    }
}