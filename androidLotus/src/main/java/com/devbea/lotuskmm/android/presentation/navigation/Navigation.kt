package com.devbea.lotuskmm.android.presentation.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.devbea.lotuskmm.android.presentation.navigation.RouteParam.RECIPE_ID
import com.devbea.lotuskmm.android.presentation.navigation.Screen.RecipeDetail
import com.devbea.lotuskmm.android.presentation.navigation.Screen.RecipeList
import com.devbea.lotuskmm.android.presentation.recipedetails.RecipeDetailScreen
import com.devbea.lotuskmm.android.presentation.recipedetails.RecipeDetailsViewModel
import com.devbea.lotuskmm.android.presentation.recipes.RecipeListScreen
import com.devbea.lotuskmm.android.presentation.recipes.RecipeListViewModel

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = RecipeList.route) {
        composable(route = RecipeList.route) {
            val viewModel = hiltViewModel<RecipeListViewModel>()
            RecipeListScreen(
                state = viewModel.state.value,
                onTriggerEvent = viewModel::onTriggerEvent,
                onSelectedRecipe = { recipeId ->
                    navController.navigate(RecipeDetail.route + "/$recipeId")
                })
        }
        composable(
            route = RecipeDetail.route + "/{$RECIPE_ID}",
            arguments = listOf(navArgument(RECIPE_ID) { type = NavType.IntType })
        ) {
            val viewMode = hiltViewModel<RecipeDetailsViewModel>()
            viewMode.recipe.value?.data?.let { RecipeDetailScreen(it) }

        }
    }

}