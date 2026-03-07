package com.example.myapp.domain.calculator.repository


import com.example.myapp.domain.calculator.model.CalculationResult

interface CalculatorRepository {
    fun calculate(expression: String): CalculationResult
    fun validateExpression(expression: String): Boolean
}