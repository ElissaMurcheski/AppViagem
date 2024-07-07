package com.example.myTrip.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun RowScope.RadioText(@StringRes text: Int) {
    Text(
        text = stringResource(text),
        fontSize = 22.sp,
        textAlign = TextAlign.Left,
        modifier = Modifier
            .weight(1.5f)
    )
}