package com.example.myapp.presentation.calculator.state

data class CalculatorState(
    val expression: String = "",
    val result: String? = null,
    val isError: Boolean = false,
    val errorMessage: String? = null
)