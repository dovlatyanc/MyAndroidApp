package com.example.myapp.presentation.calculator.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.domain.calculator.usecase.CalculateExpressionUseCase
import com.example.myapp.domain.calculator.usecase.ValidateExpressionUseCase
import com.example.myapp.presentation.calculator.state.CalculatorState
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode

class CalculatorViewModel(
    private val calculateExpressionUseCase: CalculateExpressionUseCase,
    private val validateExpressionUseCase: ValidateExpressionUseCase
) : ViewModel() {

    private val _state = MutableLiveData(CalculatorState())
    val state: LiveData<CalculatorState> = _state

    private var lastResult: Double? = null

    fun onNumberClick(number: String) {
        val currentExpression = _state.value?.expression ?: ""
        _state.value = _state.value?.copy(
            expression = currentExpression + number,
            isError = false
        )
    }

    fun onOperatorClick(operator: String) {
        val currentExpression = _state.value?.expression ?: ""

        if (currentExpression.isNotEmpty() &&
            !isLastCharOperator(currentExpression)) {

            val cleanExpression = if (currentExpression.endsWith(".")) {
                currentExpression.dropLast(1)
            } else {
                currentExpression
            }

            _state.value = _state.value?.copy(
                expression = cleanExpression + operator
            )
        } else if (currentExpression.isEmpty() && operator == "-") {
            _state.value = _state.value?.copy(
                expression = operator
            )
        }
    }

    fun onReverseClick() {
        val currentExpression = _state.value?.expression ?: ""

        if (currentExpression.isNotEmpty()) {
            val lastNumber = extractLastNumber(currentExpression)
            if (lastNumber != null) {
                val reversedNumber = reverseNumber(lastNumber)
                val newExpression = replaceLastNumber(currentExpression,
                    lastNumber, reversedNumber)
                _state.value = _state.value?.copy(
                    expression = newExpression
                )
            }
        } else if (lastResult != null) {
            lastResult = -lastResult!!
            _state.value = _state.value?.copy(
                result = formatResult(lastResult!!)
            )
        }
    }

    private fun extractLastNumber(expression: String): String? {
        val operators = setOf('+', '-', '*', '/', '%')
        var lastNumber = ""

        for (i in expression.length - 1 downTo 0) {
            val c = expression[i]
            if (c in operators && i > 0) {
                // Если нашли оператор не в начале строки
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
        return lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/' || lastChar == '%'
    }

    fun onClearClick() {
        _state.value = CalculatorState()
        lastResult = null
    }

    fun onBackspaceClick() {
        val currentExpression = _state.value?.expression ?: ""
        if (currentExpression.isNotEmpty()) {
            _state.value = _state.value?.copy(
                expression = currentExpression.dropLast(1)
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
                    .replace("%", "/100") // Обработка процентов

                val result = calculateExpressionUseCase(cleanExpression)
                if (!result.isError) {
                    lastResult = result.result
                    _state.value = _state.value?.copy(
                        expression = "",
                        result = formatResult(result.result),
                        isError = false
                    )
                } else {
                    _state.value = _state.value?.copy(
                        isError = true,
                        errorMessage = result.errorMessage ?: "Ошибка вычисления"
                    )
                }
            }
        } else if (lastResult != null) {
            _state.value = _state.value?.copy(
                result = formatResult(lastResult!!)
            )
        }
    }

    fun onDotClick() {
        val currentExpression = _state.value?.expression ?: ""


        if (currentExpression.isEmpty() || isLastCharOperator(currentExpression)) {
            _state.value = _state.value?.copy(
                expression = currentExpression + "0."
            )
        } else if (!currentExpression.contains(".") ||
            hasDecimalInLastNumber(currentExpression)) {
            return
        } else {
            _state.value = _state.value?.copy(
                expression = "$currentExpression."
            )
        }
    }

    private fun hasDecimalInLastNumber(expression: String): Boolean {
        val lastNumber = extractLastNumber(expression) ?: return false
        return lastNumber.contains(".")
    }

    fun onPercentClick() {
        val currentExpression = _state.value?.expression ?: ""

        if (currentExpression.isNotEmpty() && !isLastCharOperator(currentExpression)) {
            _state.value = _state.value?.copy(
                expression = "$currentExpression%"
            )
        }
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