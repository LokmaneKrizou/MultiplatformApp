package com.devbea.lotuskmm.android.presentation.recipes.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import com.devbea.lotuskmm.android.presentation.components.RECIPE_IMAGE_HEIGHT
import com.devbea.lotuskmm.datasource.network.RecipeServiceImpl
import com.devbea.lotuskmm.domain.model.Recipe

@Composable
fun RecipeList(
    isLoading: Boolean,
    recipes: List<Recipe>,
    page: Int,
    onTriggerNextPage: () -> Unit,
    onSelectedRecipe: (recipeId: Int) -> Unit
) {

    when {
        isLoading && recipes.isEmpty() -> {
            LoadingRecipeListShimmer(imageHeight = RECIPE_IMAGE_HEIGHT)
        }
        recipes.isEmpty() -> {
            // Empty view
        }
        else -> {
            LazyColumn {
                itemsIndexed(recipes) { index, recipe ->
                    if ((index + 1) >= (page * RecipeServiceImpl.PAGINATION_PAGE_SIZE) && !isLoading) {
                        onTriggerNextPage()
                    }
                    RecipeCard(recipe = recipe) {
                        onSelectedRecipe(recipe.id)
                    }
                }
            }
        }
    }
}