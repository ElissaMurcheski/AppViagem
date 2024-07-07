package com.example.myTrip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myTrip.ui.screns.MainScreen
import com.example.myTrip.ui.screns.SignInScreen
import com.example.myTrip.ui.screns.SignUpScreen
import com.example.myTrip.ui.theme.MyTripTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTripTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController, "signIn") {
                        composable("signIn") { SignInScreen(navController) }
                        composable("signUp") { SignUpScreen(navController) }
                        composable(
                            route = "main/{userId}",
                            arguments = listOf(navArgument("userId") { type = NavType.LongType })
                        ) { backStackEntry ->
                            val userId = backStackEntry.arguments?.getLong("userId")
                            MainScreen(navController, userId ?: 0)
                        }
                    }
                }
            }
        }
    }
}