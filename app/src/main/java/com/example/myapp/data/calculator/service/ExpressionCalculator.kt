package com.example.myapp.data.calculator.service

import com.example.myapp.domain.calculator.model.CalculationResult
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
                        message = "Некорректный результат вычисления"
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
                message = e.message ?: "Ошибка вычисления"
            )
        }
    }
}