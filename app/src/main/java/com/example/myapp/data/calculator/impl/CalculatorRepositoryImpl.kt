package com.example.myapp.data.calculator.repository

import com.example.myapp.domain.calculator.model.CalculationResult
import com.example.myapp.domain.calculator.repository.CalculatorRepository
import com.example.myapp.data.calculator.service.ExpressionCalculator

class CalculatorRepositoryImpl(
    private val calculator: ExpressionCalculator
) : CalculatorRepository {

    override fun calculate(expression: String): CalculationResult {
        return try {
            val result = calculator.calculate(expression)
            CalculationResult(
                expression = expression,
                result = result,
                isError = false
            )
        } catch (e: Exception) {
            CalculationResult(
                expression = expression,
                result = 0.0,
                isError = true,
                errorMessage = e.message
            )
        }
    }

    override fun validateExpression(expression: String): Boolean {
        return calculator.isValid(expression)
    }
}