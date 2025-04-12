package com.amits.quickuserapp.ui.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun UserApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            UserListScreen { userId ->
                navController.navigate("detail/$userId")
            }
        }
        composable("detail/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull()
            userId?.let {
                // Render the UserDetailScreen with the provided userId
                UserDetailScreen(navController = navController, userId = it)
            }
        }
    }
}