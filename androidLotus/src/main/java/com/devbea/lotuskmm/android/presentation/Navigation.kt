package com.devbea.lotuskmm.android.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.devbea.lotuskmm.android.presentation.Screen.RecipeDetail
import com.devbea.lotuskmm.android.presentation.Screen.RecipeList
import com.devbea.lotuskmm.android.presentation.recipedetails.RecipeDetailScreen
import com.devbea.lotuskmm.android.presentation.recipes.RecipeListScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = RecipeList.route) {
        composable(route = RecipeList.route) { navBackStackEntry ->
            RecipeListScreen(onSelectedRecipe = { recipeId ->
                navController.navigate(RecipeDetail.route + "/$recipeId")
            })
        }
        composable(
            route = RecipeDetail.route + "/{${RECIPE_ID}}",
            arguments = listOf(navArgument(RECIPE_ID) { type = NavType.IntType })
        ) { navBackStackEntry ->
            RecipeDetailScreen(recipeId = navBackStackEntry.arguments?.getInt(RECIPE_ID))
        }
    }

}


private const val RECIPE_ID = "recipeId"