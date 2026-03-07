package com.example.myapp.data.calculator.service

import net.objecthunter.exp4j.ExpressionBuilder

class ExpressionCalculator {

    fun calculate(expression: String): Double {
        return try {
            val exp = ExpressionBuilder(expression).build()
            exp.evaluate()
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid expression: ${e.message}")
        }
    }

    fun isValid(expression: String): Boolean {
        return try {
            val exp = ExpressionBuilder(expression).build()
            exp.evaluate()
            true
        } catch (e: Exception) {
            false
        }
    }
}