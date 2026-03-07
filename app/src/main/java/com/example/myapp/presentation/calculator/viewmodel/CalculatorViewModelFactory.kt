package com.example.myapp.presentation.calculator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapp.domain.calculator.usecase.CalculateExpressionUseCase
import com.example.myapp.domain.calculator.usecase.ValidateExpressionUseCase

class CalculatorViewModelFactory(
    private val calculateExpressionUseCase: CalculateExpressionUseCase,
    private val validateExpressionUseCase: ValidateExpressionUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalculatorViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CalculatorViewModel(calculateExpressionUseCase, validateExpressionUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}