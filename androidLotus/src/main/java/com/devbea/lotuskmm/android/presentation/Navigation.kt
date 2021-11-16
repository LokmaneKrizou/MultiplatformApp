package com.devbea.lotuskmm.android.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devbea.lotuskmm.android.presentation.Screen.RecipeDetail
import com.devbea.lotuskmm.android.presentation.Screen.RecipeList

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = RecipeList.route) {
        composable(route = RecipeList.route) { navBackStackEntry ->
            Column {
                Text(text = "Recipe List Screen")
                Divider()
                Button(onClick = { navController.navigate(RecipeDetail.route) }) {
                    Text(text = "Go to recipe detail")
                }

            }
        }
        composable(route = RecipeDetail.route) { navBackStackEntry ->
            Text(text = "Recipe Detail Screen")
        }
    }

}