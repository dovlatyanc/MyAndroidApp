package com.example.myapp.domain.calculator.usecase

import com.example.myapp.domain.calculator.repository.CalculatorRepository

class ValidateExpressionUseCase(
    private val repository: CalculatorRepository
) {
    operator fun invoke(expression: String): Boolean {
        return repository.validateExpression(expression)
    }
}