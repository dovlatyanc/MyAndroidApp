package com.example.myapp.domain.calculator.usecase

import com.example.myapp.domain.calculator.model.CalculationResult
import com.example.myapp.domain.calculator.repository.CalculatorRepository

class CalculateExpressionUseCase(
    private val repository: CalculatorRepository
) {
    operator fun invoke(expression: String): CalculationResult {
        return repository.calculate(expression)
    }
}