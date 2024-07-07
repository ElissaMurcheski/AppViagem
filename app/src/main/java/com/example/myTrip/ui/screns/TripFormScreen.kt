package com.example.myTrip.ui.screns

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myTrip.R
import com.example.myTrip.components.ButtonFillMaxWidth
import com.example.myTrip.components.InputDate
import com.example.myTrip.components.InputNumber
import com.example.myTrip.components.InputText
import com.example.myTrip.components.Label
import com.example.myTrip.components.RadioText
import com.example.myTrip.database.AppDatabase
import com.example.myTrip.models.TripType
import com.example.myTrip.viewmodels.TripViewModel
import com.example.myTrip.viewmodels.TripViewModelFactory

@Composable
fun TripFormScreen(navController: NavHostController, tripId: Long? = null) {
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val tripViewModel: TripViewModel = viewModel(
        factory = TripViewModelFactory(db)
    )
    var trip = tripViewModel.uiState.collectAsState()
    if (trip.value.isEmpty() && tripId != null) {
        tripViewModel.findById(tripId)
        trip = tripViewModel.uiState.collectAsState()
    }

    Column {
        Row {
            Label(R.string.source)
        }
        Row {
            InputText(trip.value.source, { tripViewModel.updateSource(it) })
        }
        Row {
            Label(R.string.type)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = trip.value.type == TripType.LEISURE,
                onClick = { tripViewModel.updateType(TripType.LEISURE) },
                modifier = Modifier.weight(0.5f)
            )
            RadioText(text = R.string.leisure)
            RadioButton(
                selected = trip.value.type == TripType.BUSINESS,
                onClick = { tripViewModel.updateType(TripType.BUSINESS) },
                modifier = Modifier.weight(0.5f)
            )
            RadioText(text = R.string.business)
        }
        Row {
            Label(R.string.start)
        }
        Row {
            InputDate(trip.value.startDate) { tripViewModel.updateStartDate(it) }
        }
        Row {
            Label(R.string.end)
        }
        Row {
            InputDate(trip.value.endDate) { tripViewModel.updateEndDate(it) }
        }
        Row {
            Label(R.string.budget)
        }
        Row {
            InputNumber(trip.value.budget.toString()) { tripViewModel.updateBudget(it.toDouble()) }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            ButtonFillMaxWidth(R.string.save, {
                if (trip.value.hasEmpty()) {
                    Toast
                        .makeText(context, "Campo não preenchido.", Toast.LENGTH_SHORT)
                        .show()
                    return@ButtonFillMaxWidth
                }
                tripViewModel.save()
                navController.navigate("trip")
                Toast
                    .makeText(context, "Cadastrado com sucesso!.", Toast.LENGTH_SHORT)
                    .show()
            })
        }
    }
}

@Preview
@Composable
fun TripFormScreenPreview() {
    TripFormScreen(rememberNavController())
}