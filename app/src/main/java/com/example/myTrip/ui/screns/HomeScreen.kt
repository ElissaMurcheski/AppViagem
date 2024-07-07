package com.example.myTrip.ui.screns

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myTrip.database.AppDatabase
import com.example.myTrip.viewmodels.UserViewModel
import com.example.myTrip.viewmodels.UserViewModelFactory

@Composable
fun HomeScreen(navController: NavHostController, userId: Long) {
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val userViewModel: UserViewModel = viewModel(
        factory = UserViewModelFactory(db)
    )
    userViewModel.findById(userId)
    val user = userViewModel.uiState.collectAsState()
    Column {
        Row {
            Text(
                "Id: ${user.value.id}",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Row {
            Text(
                "Usuario: ${user.value.username}",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            );
        }
        Row {
            Text(
                "E-mail: ${user.value.email}",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(rememberNavController(), 0)
}