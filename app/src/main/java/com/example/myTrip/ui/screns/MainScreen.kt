package com.example.myTrip.ui.screns

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

private fun isSelected(currentDestination: NavDestination?, route: String): Boolean {
    return currentDestination?.hierarchy?.any { it.route == route } == true
}

@Composable
fun MainScreen(mainActivityNavController: NavHostController, userId: Long) {
    val navController = rememberNavController()
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    "Viagem",
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth(0.9f)
                )
                IconButton(onClick = { mainActivityNavController.navigate("signIn") }) {
                    Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = "Sair")
                }
            },
        )
    }, bottomBar = {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry.value?.destination

        BottomNavigation {
            BottomNavigationItem(selected = isSelected(currentDestination, "home"),
                onClick = { navController.navigate("home") },
                icon = {
                    Icon(imageVector = Icons.Filled.Home, contentDescription = "Principal")
                })

            BottomNavigationItem(selected = isSelected(currentDestination, "trip"),
                onClick = { navController.navigate("trip") },
                icon = {
                    Icon(imageVector = Icons.Filled.LocationOn, contentDescription = "Viagem")
                })

            BottomNavigationItem(selected = isSelected(currentDestination, "about"),
                onClick = { navController.navigate("about") },
                icon = {
                    Icon(imageVector = Icons.Filled.Info, contentDescription = "Sobre")
                })
        }
    }) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(10.dp)
        ) {
            NavHost(navController, startDestination = "home") {
                composable("home") {
                    HomeScreen(navController, userId);
                }
                composable("trip") {
                    TripScreen(navController)
                }
                composable("about") {
                    AboutScreen(navController)
                }
                composable("tripForm") {
                    TripFormScreen(navController)
                }
                composable(
                    route = "tripForm/{tripId}",
                    arguments = listOf(navArgument("tripId") { type = NavType.LongType })
                ) { backStackEntry ->
                    val tripId = backStackEntry.arguments?.getLong("tripId")
                    TripFormScreen(navController, tripId ?: 0)
                }
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(rememberNavController(), 0)
}