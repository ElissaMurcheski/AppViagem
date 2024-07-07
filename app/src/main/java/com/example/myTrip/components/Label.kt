package com.example.myTrip.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun RowScope.Label(@StringRes text: Int) {
    Text(
        text = stringResource(text),
        textAlign = TextAlign.Left,
        fontSize = 20.sp,
        modifier = Modifier.fillMaxWidth()
    )
}