package com.example.myapp.domain.calculator.model

enum class CalculationErrorCode {
    INVALID_RESULT,      // NaN, Infinity
    INVALID_EXPRESSION,  // ошибка парсинга выражения
    UNKNOWN_ERROR        // любая другая ошибка
}

sealed class CalculationResult {

    data class Success(
        val expression: String,
        val value: Double
    ) : CalculationResult()

    data class Error(
        val expression: String,
        val errorCode: CalculationErrorCode,
        val originalException: Exception? = null
    ) : CalculationResult()
}