package com.devbea.lotuskmm.android.presentation

sealed class Screen(val route: String) {
    object RecipeList : Screen("recipeList")
    object RecipeDetail : Screen("recipeDetail")


}
