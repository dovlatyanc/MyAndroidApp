package com.example.myapp.presentation.calculator.utils

import android.content.Context
import com.example.myapp.R
import com.example.myapp.domain.calculator.model.CalculationErrorCode

fun CalculationErrorCode.toUserMessage(
    context: Context,
    expression: String = ""
): String {
    return when (this) {
        CalculationErrorCode.INVALID_RESULT ->
            context.getString(R.string.error_calc_invalid_result)

        CalculationErrorCode.INVALID_EXPRESSION ->
            context.getString(R.string.error_calc_invalid_expression, expression)

        CalculationErrorCode.UNKNOWN_ERROR ->
            context.getString(R.string.error_calc_unknown)
    }
}