package com.example.myapp.data.calculator.service

import com.example.myapp.domain.calculator.model.CalculationResult
import com.example.myapp.domain.calculator.model.CalculationErrorCode
import net.objecthunter.exp4j.ExpressionBuilder

class ExpressionCalculator {

    fun calculate(expression: String): CalculationResult {
        return try {
            val exp = ExpressionBuilder(expression).build()
            val result = exp.evaluate()

            when {
                result.isNaN() || result.isInfinite() -> {
                    CalculationResult.Error(
                        expression = expression,
                        errorCode = CalculationErrorCode.INVALID_RESULT
                    )
                }
                else -> {
                    CalculationResult.Success(
                        expression = expression,
                        value = result
                    )
                }
            }
        } catch (e: Exception) {
            CalculationResult.Error(
                expression = expression,
                errorCode = CalculationErrorCode.INVALID_EXPRESSION,
                originalException = e
            )
        }
    }
}