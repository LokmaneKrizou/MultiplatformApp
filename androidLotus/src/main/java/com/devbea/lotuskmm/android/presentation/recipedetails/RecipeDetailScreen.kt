package com.devbea.lotuskmm.android.presentation.recipedetails

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.tooling.preview.Preview
import com.devbea.lotuskmm.android.presentation.recipedetails.component.RecipeView
import com.devbea.lotuskmm.android.presentation.theme.AppTheme
import com.devbea.lotuskmm.datasource.network.model.RecipeDto
import com.devbea.lotuskmm.datasource.network.toRecipe
import com.devbea.lotuskmm.presentation.recipe_detail.RecipeDetailEvents
import com.devbea.lotuskmm.presentation.recipe_detail.RecipeDetailState

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun RecipeDetailScreen(
    state: RecipeDetailState,
    onTriggerEvent: (RecipeDetailEvents) -> Unit
) {
    AppTheme(displayProgressBar = state.isLoading) {
        when {
            state.isLoading && state.recipe == null -> {

            }
            state.recipe == null -> {
            }
            else -> {
                RecipeView(state.recipe!!)
            }
        }
    }
}