package com.example.myTrip.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun RowScope.InputDate(value: String, onValueChange: (String) -> Unit) {
    val showDialog = remember {
        mutableStateOf(false)
    }
    val datePickerState = rememberDatePickerState()

    if (showDialog.value) {
        DatePickerDialog(onDismissRequest = { showDialog.value = false }, confirmButton = {
            Button(onClick = {
                datePickerState.selectedDateMillis?.let { millis ->
                    onValueChange(millis.toBrazilianDateFormat())
                }
                showDialog.value = false
            }) {
                Text(text = "Escolher data")
            }
        }, modifier = Modifier.weight(4f)
        ) {
            DatePicker(state = datePickerState)
        }
    }
    InputText(value, { }, Modifier.onFocusChanged {
        if (it.isFocused) {
            showDialog.value = true
        }
    }, readOnly = true
    )
}

private fun Long.toBrazilianDateFormat(pattern: String = "dd/MM/yyyy"): String {
    val date = Date(this)
    val formatter = SimpleDateFormat(pattern, Locale("pt-br")).apply {
        timeZone = TimeZone.getTimeZone("GMT")
    }
    return formatter.format(date)
}