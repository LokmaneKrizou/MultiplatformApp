package com.devbea.lotuskmm.presentation.recipe_detail

import com.devbea.lotuskmm.domain.model.Recipe

data class RecipeDetailState(
    val isLoading: Boolean = false,
    val recipe: Recipe? = null
)