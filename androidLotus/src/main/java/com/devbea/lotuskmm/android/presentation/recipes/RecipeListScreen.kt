package com.devbea.lotuskmm.android.presentation.recipes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devbea.lotuskmm.datasource.network.model.RecipeDto
import com.devbea.lotuskmm.datasource.network.toRecipe
import com.devbea.lotuskmm.domain.model.Recipe
import com.devbea.lotuskmm.domain.util.DataState

@Composable
fun RecipeListScreen(screenState: DataState<List<Recipe>>?, onSelectedRecipe: ((Int) -> Unit)) {
    screenState?.data?.let { recipes ->
        LazyColumn {
            items(recipes.size) { recipeId ->
                val recipe = recipes[recipeId]
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelectedRecipe(recipe.id) }) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = "Recipe = ${recipe.title}"
                    )
                }

            }
        }
    }
}

@Preview
@Composable
private fun RecipeListScreenPreview() {
    RecipeListScreen(
        DataState.data(
            data = listOf(
                RecipeDto(
                    1,
                    "title",
                    "me",
                    "",
                    5,
                    "",
                    listOf(),
                    123,
                    123
                ).toRecipe()
            )
        )
    ) {}
}