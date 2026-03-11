package com.example.myapp.domain.calculator.model

sealed class CalculationResult {

    data class Success(
        val expression: String,
        val value: Double
    ) : CalculationResult()

    data class Error(
        val expression: String,
        val message: String
    ) : CalculationResult()
}