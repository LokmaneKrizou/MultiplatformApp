package com.devbea.lotuskmm.android.presentation.recipedetails

import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devbea.lotuskmm.android.presentation.components.RECIPE_IMAGE_HEIGHT
import com.devbea.lotuskmm.android.presentation.recipedetails.component.RecipeView
import com.devbea.lotuskmm.android.presentation.recipes.components.LoadingRecipeShimmer
import com.devbea.lotuskmm.android.presentation.theme.AppTheme
import com.devbea.lotuskmm.presentation.recipe_detail.RecipeDetailEvents
import com.devbea.lotuskmm.presentation.recipe_detail.RecipeDetailState

@ExperimentalStdlibApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun RecipeDetailScreen(
    state: RecipeDetailState,
    onTriggerEvent: (RecipeDetailEvents) -> Unit
) {
    AppTheme(
        displayProgressBar = state.isLoading,
        dialogQueue = state.queue,
        onRemoveHeadFromQueue = { onTriggerEvent(RecipeDetailEvents.OnRemoveHeadMessageFromQueue) }
    ) {
        when {
            state.isLoading && state.recipe == null -> {
                LoadingRecipeShimmer(
                    imageHeight = RECIPE_IMAGE_HEIGHT,
                    numberOfLines = 4,
                    numberOfItems = 1
                )
            }
            state.recipe == null -> {
                Text(
                    text = "We were unable to retrieve the details for this recipe. \n Try resetting the app.",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.body1
                )
            }
            else -> {
                RecipeView(state.recipe!!)
            }
        }
    }
}