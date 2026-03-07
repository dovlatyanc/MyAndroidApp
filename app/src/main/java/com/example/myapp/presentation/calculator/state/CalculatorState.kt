package com.example.myapp.presentation.calculator.state

data class CalculatorState(
    val expression: String = "",
    val result: String = "0",
    val isError: Boolean = false,
    val errorMessage: String? = null
)