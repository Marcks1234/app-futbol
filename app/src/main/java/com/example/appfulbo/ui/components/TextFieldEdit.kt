package com.example.appfulbo.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation


enum class TypeText {
    EMAIL,
    PASSWORD,
    NUMBER,
    DECIMAL,
    TEXT
}

@Composable
fun TextFieldEdit(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String = "",
    label: String = "",
    onValueChange: (String) -> Unit,
    typeText: TypeText = TypeText.TEXT,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    suffix: String = "",
) {
    OutlinedTextField(
        modifier = modifier,
        onValueChange = { onValueChange(it) },
        value = value,
        visualTransformation =
            when (typeText) {
                TypeText.PASSWORD -> PasswordVisualTransformation()
                else -> VisualTransformation.None
            },
        keyboardOptions =
            KeyboardOptions(
                keyboardType =
                    when (typeText) {
                        TypeText.PASSWORD -> KeyboardType.Password
                        TypeText.NUMBER -> KeyboardType.Number
                        TypeText.EMAIL -> KeyboardType.Email
                        TypeText.DECIMAL -> KeyboardType.Decimal
                        else -> KeyboardType.Text
                    },
            ),
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        maxLines = 1,
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            cursorColor = Color.Black,
        ),
        placeholder = { Text(placeholder) },
        supportingText = supportingText,
        suffix = { Text(suffix) },
        label =
            if (label.isNotBlank()) {
                { Text(label) }
            } else null,
        )
}