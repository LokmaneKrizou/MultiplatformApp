package com.devbea.lotuskmm.android.presentation.recipedetails

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.tooling.preview.Preview
import com.devbea.lotuskmm.android.presentation.theme.AppTheme
import com.devbea.lotuskmm.datasource.network.model.RecipeDto
import com.devbea.lotuskmm.datasource.network.toRecipe
import com.devbea.lotuskmm.domain.model.Recipe

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun RecipeDetailScreen(recipe: Recipe?) {
    AppTheme(displayProgressBar = false) {
        Text(
            style = MaterialTheme.typography.body1,
            text = if (recipe != null) "Recipe Detail Screen $recipe" else "Error something went wrong"
        )
    }
}


@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Preview
@Composable
private fun RecipeDetailScreenPreview() {
    RecipeDetailScreen(
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
}