package com.example.myapp.presentation.calculator.state

import com.example.myapp.domain.calculator.model.CalculationErrorCode

data class CalculatorState(
    val expression: String = "",
    val result: String = "",
    val isError: Boolean = false,
    val errorCode: CalculationErrorCode? = null,
    val errorExpression: String = ""
)