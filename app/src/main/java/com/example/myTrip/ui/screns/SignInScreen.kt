package com.example.myTrip.ui.screns

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myTrip.R
import com.example.myTrip.components.ButtonFillMaxWidth
import com.example.myTrip.components.ButtonFillMaxWidthElevated
import com.example.myTrip.components.InputPassword
import com.example.myTrip.components.InputText
import com.example.myTrip.components.Label
import com.example.myTrip.database.AppDatabase
import com.example.myTrip.viewmodels.UserViewModel
import com.example.myTrip.viewmodels.UserViewModelFactory

@Composable
fun SignInScreen(navController: NavHostController) {
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val userViewModel: UserViewModel = viewModel(
        factory = UserViewModelFactory(db)
    )
    val user = userViewModel.uiState.collectAsState()
    val users = userViewModel.getAll().collectAsState(initial = emptyList())

    Column(Modifier.padding(10.dp)) {
        Row {
            Image(
                painter = painterResource(R.mipmap.ic_user_icon_foreground),
                contentDescription = "User Icon",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
        }
        Row {
            Label(R.string.username)
        }
        Row {
            InputText(value = user.value.username, { userViewModel.updateUsername(it) })
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Label(R.string.password)
        }
        Row {
            InputPassword(value = user.value.password) { userViewModel.updatePassword(it) }
        }
        Spacer(modifier = Modifier.height(40.dp))
        Row {
            ButtonFillMaxWidth(R.string.signIn, {
                val found =
                    users.value.firstOrNull { it.username == user.value.username && it.password == user.value.password }
                if (found != null) {
                    navController.navigate("main/${found.id}")
                    Toast.makeText(context, "Logado com sucesso!.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Usuário ou senha inválida!", Toast.LENGTH_SHORT).show()
                }

            })
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            ButtonFillMaxWidthElevated(R.string.signUp) {
                navController.navigate("signUp")
            }
        }
    }
}

@Preview
@Composable
fun SignInScreenPreview() {
    SignInScreen(rememberNavController())
}