package com.devbea.lotuskmm.presentation.recipe_list

import com.devbea.lotuskmm.domain.model.Recipe

data class RecipeListState(
    val isLoading: Boolean = false,
    val loaderPosition: Float = ScreenPosition.TOP.value,
    val page: Int = 1,
    val query: String = "",
    val selectedCategory: FoodCategory? = null,
    val recipes: List<Recipe> = listOf()
)