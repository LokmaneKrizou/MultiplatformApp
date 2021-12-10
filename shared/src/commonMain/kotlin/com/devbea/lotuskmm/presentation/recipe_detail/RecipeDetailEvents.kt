package com.devbea.lotuskmm.presentation.recipe_detail

sealed class RecipeDetailEvents {
    class LoadRecipe(val id: Int) : RecipeDetailEvents()
    object OnRemoveHeadMessageFromQueue : RecipeDetailEvents()
}