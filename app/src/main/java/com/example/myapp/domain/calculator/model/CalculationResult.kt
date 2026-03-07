package com.example.myapp.domain.calculator.model

data class CalculationResult(
    val expression: String,
    val result: Double,
    val isError: Boolean = false,
    val errorMessage: String? = null
)