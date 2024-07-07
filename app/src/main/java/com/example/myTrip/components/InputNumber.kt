package com.example.myTrip.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun RowScope.InputNumber(value: String, onValueChange: (String) -> Unit) {
    InputText(
        value, onValueChange = {
            if (it.isEmpty()){
                onValueChange("0.0")
            } else {
                onValueChange(when (it.toDoubleOrNull()) {
                    null -> value //old value
                    else -> it   //new value
                })
            }
        }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}