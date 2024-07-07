package com.example.myTrip.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

@Composable
fun RowScope.InputEmail(value: String, onValueChange: (String) -> Unit) {
    InputText(value, onValueChange)
}