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
import com.example.myTrip.components.InputPassword
import com.example.myTrip.components.InputText
import com.example.myTrip.components.Label
import com.example.myTrip.database.AppDatabase
import com.example.myTrip.viewmodels.UserViewModel
import com.example.myTrip.viewmodels.UserViewModelFactory

@Composable
fun SignUpScreen(navController: NavHostController) {
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val userViewModel: UserViewModel = viewModel(
        factory = UserViewModelFactory(db)
    )
    val user = userViewModel.uiState.collectAsState()

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
            InputText(user.value.username, { userViewModel.updateUsername(it) })
        }
        Spacer(Modifier.height(20.dp))
        Row {
            Label(R.string.email)
        }
        Row {
            InputText(user.value.email, { userViewModel.updateEmail(it) })
        }
        Spacer(Modifier.height(20.dp))
        Row {
            Label(R.string.password)
        }
        Row {
            InputPassword(user.value.password) { userViewModel.updatePassword(it) }
        }
        Spacer(Modifier.height(20.dp))
        Row {
            ButtonFillMaxWidth(R.string.signUp, {
                if (user.value.hasEmpty()) {
                    Toast
                        .makeText(context, "Campo não preenchido.", Toast.LENGTH_SHORT)
                        .show()
                    return@ButtonFillMaxWidth
                }
                userViewModel.save()
                navController.navigate("signIn")
                Toast
                    .makeText(context, "Cadastrado com sucesso!", Toast.LENGTH_SHORT)
                    .show()
            })
        }
    }
}

@Preview
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(rememberNavController())
}