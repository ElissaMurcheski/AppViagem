package com.example.myTrip.ui.screns

import android.app.AlertDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myTrip.R
import com.example.myTrip.database.AppDatabase
import com.example.myTrip.models.Trip
import com.example.myTrip.models.TripType
import com.example.myTrip.viewmodels.TripViewModel
import com.example.myTrip.viewmodels.TripViewModelFactory

@Composable
fun TripScreen(navController: NavHostController) {
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val tripViewModel: TripViewModel = viewModel(
        factory = TripViewModelFactory(db)
    )

    val trips = tripViewModel.getAll().collectAsState(initial = emptyList())
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            navController.navigate("tripForm")
        }) {
            Icon(
                imageVector = Icons.Default.AddCircle, contentDescription = ""
            )
        }
    }) {
        Column(modifier = Modifier.padding(it)) {
            LazyColumn {
                items(items = trips.value) {
                    TripCard(it, tripViewModel, navController)
                }
            }
        }
    }
}

@Composable
fun TripCard(trip: Trip, tripViewModel: TripViewModel, navController: NavHostController) {
    val context = LocalContext.current
    ElevatedCard(elevation = CardDefaults.cardElevation(
        defaultElevation = 2.dp
    ), modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 6.dp)
        .pointerInput(Unit) {
            detectTapGestures(onTap = {
                navController.navigate("tripForm/${trip.id}")
            }, onLongPress = {
                val builder = AlertDialog.Builder(context)
                builder
                    .setMessage("Deseja deletar permanentemente?")
                    .setCancelable(false)
                    .setPositiveButton("Sim") { dialog, id ->
                        tripViewModel.delete(trip)
                    }
                    .setNegativeButton("Não") { dialog, id ->
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
            })
        }) {
        Column(modifier = Modifier.padding(15.dp)) {
            Row {
                Column {
                    Row {
                        var painter = painterResource(id = R.drawable.leisure_trip)
                        if (trip.type == TripType.BUSINESS) {
                            painter = painterResource(id = R.drawable.business_trip)
                        }
                        Image(
                            painter,
                            contentDescription = "Icone Viagem",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(40.dp)
                        )
                        Text(
                            text = trip.source,
                            modifier = Modifier.padding(6.dp),
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 20.sp,
                        )
                    }
                }
                Column {
                    Row {
                        Text(
                            text = "R$ ${trip.budget}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 4.dp),
                            textAlign = TextAlign.Right,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Row {
                        Text(
                            text = "${trip.startDate} - ${trip.endDate}",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Right,
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }

                }
            }
        }
    }
}

@Preview
@Composable
fun TripScreenPreview() {
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val tripViewModel: TripViewModel = viewModel(
        factory = TripViewModelFactory(db)
    )

    val trip = Trip(1, "Teste", "Teste", "Teste", 1999.99, TripType.LEISURE)
    TripCard(trip, tripViewModel, rememberNavController())
}
