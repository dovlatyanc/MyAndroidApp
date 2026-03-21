package com.example.myapp.presentation.core.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.myapp.presentation.core.ui.UiText.*

sealed class UiText {
    data class DynamicString(val value: String) : UiText()
    data class StringResource(
        val resId: Int,
        val args: Array<Any> = emptyArray()
    ) : UiText() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as StringResource

            if (resId != other.resId) return false
            if (!args.contentEquals(other.args)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = resId
            result = 31 * result + args.contentHashCode()
            return result
        }
    }

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(resId, *args)
        }
    }

    fun getString(context: Context): String = asString(context)
}

