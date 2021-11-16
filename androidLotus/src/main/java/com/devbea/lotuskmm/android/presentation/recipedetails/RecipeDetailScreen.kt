package com.devbea.lotuskmm.android.presentation.recipedetails

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun RecipeDetailScreen(recipeId: Int?) {
    Text(text = if (recipeId != null) "Recipe Detail Screen $recipeId" else "Error something went wrong")
}


@Preview
@Composable
private fun RecipeDetailScreenPreview() {
    RecipeDetailScreen(1)
}