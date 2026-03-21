
package com.example.myapp.presentation.calculator.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.data.calculator.service.ExpressionCalculator
import com.example.myapp.domain.calculator.model.CalculationResult
import com.example.myapp.presentation.calculator.state.CalculatorState
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode

class CalculatorViewModel(
    private val calculator: ExpressionCalculator
) : ViewModel() {

    private val _state = MutableLiveData(CalculatorState())
    val state: LiveData<CalculatorState> = _state

    private var lastResult: Double? = null

    fun onNumberClick(number: String) {
        val currentExpression = _state.value?.expression ?: ""
        _state.value = _state.value?.copy(
            expression = currentExpression + number,
            isError = false,
            errorCode = null,
            errorExpression = ""
        )
    }

    fun onOperatorClick(operator: String) {
        val currentExpression = _state.value?.expression ?: ""

        if (currentExpression.isNotEmpty() && !isLastCharOperator(currentExpression)) {
            val cleanExpression = if (currentExpression.endsWith(".")) {
                currentExpression.dropLast(1)
            } else {
                currentExpression
            }
            _state.value = _state.value?.copy(
                expression = cleanExpression + operator,
                isError = false,
                errorCode = null,
                errorExpression = ""
            )
        } else if (currentExpression.isEmpty() && operator == "-") {
            _state.value = _state.value?.copy(
                expression = operator,
                isError = false,
                errorCode = null,
                errorExpression = ""
            )
        }
    }

    fun onReverseClick() {
        val currentExpression = _state.value?.expression ?: ""

        if (currentExpression.isNotEmpty()) {
            val lastNumber = extractLastNumber(currentExpression)
            if (lastNumber != null) {
                val reversedNumber = reverseNumber(lastNumber)
                val newExpression = replaceLastNumber(currentExpression, lastNumber, reversedNumber)
                _state.value = _state.value?.copy(
                    expression = newExpression,
                    isError = false,
                    errorCode = null,
                    errorExpression = ""
                )
            }
        } else if (lastResult != null) {
            lastResult = -lastResult!!
            _state.value = _state.value?.copy(
                result = formatResult(lastResult!!),
                isError = false,
                errorCode = null,
                errorExpression = ""
            )
        }
    }

    fun onClearClick() {
        _state.value = CalculatorState()
        lastResult = null
    }

    fun onBackspaceClick() {
        val currentExpression = _state.value?.expression ?: ""
        if (currentExpression.isNotEmpty()) {
            _state.value = _state.value?.copy(
                expression = currentExpression.dropLast(1),
                isError = false,
                errorCode = null,
                errorExpression = ""
            )
        }
    }

    fun onEqualsClick() {
        val expression = _state.value?.expression ?: ""
        if (expression.isNotEmpty()) {
            viewModelScope.launch {
                val cleanExpression = expression
                    .replace("×", "*")
                    .replace("−", "-")
                    .replace("%", "/100")


                when (val calculationResult = calculator.calculate(cleanExpression)) {
                    is CalculationResult.Success -> {
                        lastResult = calculationResult.value
                        _state.value = _state.value?.copy(
                            expression = "",
                            result = formatResult(calculationResult.value),
                            isError = false,
                            errorCode = null,
                            errorExpression = ""
                        )
                    }
                    is CalculationResult.Error -> {
                        _state.value = _state.value?.copy(
                            isError = true,
                            errorCode = calculationResult.errorCode,
                            errorExpression = calculationResult.expression
                        )
                    }
                }
            }
        } else if (lastResult != null) {
            _state.value = _state.value?.copy(
                result = formatResult(lastResult!!)
            )
        }
    }

    fun onErrorShown() {
        _state.value = _state.value?.copy(
            isError = false,
            errorCode = null,
            errorExpression = ""
        )
    }

    fun onDotClick() {
        val currentExpression = _state.value?.expression ?: ""

        if (currentExpression.isEmpty() || isLastCharOperator(currentExpression)) {
            _state.value = _state.value?.copy(
                expression = currentExpression + "0.",
                isError = false,
                errorCode = null,
                errorExpression = ""
            )
        } else if (!currentExpression.contains(".") || hasDecimalInLastNumber(currentExpression)) {
            return
        } else {
            _state.value = _state.value?.copy(
                expression = "$currentExpression.",
                isError = false,
                errorCode = null,
                errorExpression = ""
            )
        }
    }

    fun onPercentClick() {
        val currentExpression = _state.value?.expression ?: ""

        if (currentExpression.isNotEmpty() && !isLastCharOperator(currentExpression)) {
            _state.value = _state.value?.copy(
                expression = "$currentExpression%",
                isError = false,
                errorCode = null,
                errorExpression = ""
            )
        }
    }


    private fun extractLastNumber(expression: String): String? {
        val operators = setOf('+', '-', '*', '/', '%')
        var lastNumber = ""

        for (i in expression.length - 1 downTo 0) {
            val c = expression[i]
            if (c in operators && i > 0) {
                lastNumber = expression.substring(i + 1)
                break
            } else if (i == 0) {
                lastNumber = expression.substring(i)
            }
        }

        return if (lastNumber.isNotEmpty() && lastNumber.matches(Regex("-?\\d*\\.?\\d+"))) {
            lastNumber
        } else {
            null
        }
    }

    private fun reverseNumber(number: String): String {
        return try {
            val value = number.toDouble()
            val reversed = -value
            if (reversed % 1 == 0.0) {
                reversed.toInt().toString()
            } else {
                reversed.toString()
            }
        } catch (e: NumberFormatException) {
            number
        }
    }

    private fun replaceLastNumber(expression: String, oldNumber: String, newNumber: String): String {
        val lastIndex = expression.lastIndexOf(oldNumber)
        return if (lastIndex >= 0) {
            expression.substring(0, lastIndex) + newNumber
        } else {
            expression
        }
    }

    private fun isLastCharOperator(expression: String): Boolean {
        if (expression.isEmpty()) return false
        val lastChar = expression.last()
        return lastChar in setOf('+', '-', '*', '/', '%')
    }

    private fun hasDecimalInLastNumber(expression: String): Boolean {
        val lastNumber = extractLastNumber(expression) ?: return false
        return lastNumber.contains(".")
    }

    @SuppressLint("DefaultLocale")
    private fun formatResult(result: Double): String {
        return try {
            val bd = BigDecimal(result).setScale(10, RoundingMode.HALF_UP)
            var str = bd.stripTrailingZeros().toPlainString()

            if (str.contains(".")) {
                str = str.trimEnd('0').trimEnd('.')
            }
            str
        } catch (e: Exception) {
            if (result % 1 == 0.0) {
                result.toInt().toString()
            } else {
                String.format("%.5f", result).trimEnd('0').trimEnd('.')
            }
        }
    }
}