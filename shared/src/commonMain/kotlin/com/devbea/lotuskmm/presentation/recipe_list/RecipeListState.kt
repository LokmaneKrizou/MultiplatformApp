package com.devbea.lotuskmm.presentation.recipe_list

import com.devbea.lotuskmm.domain.model.GenericMessageInfo
import com.devbea.lotuskmm.domain.model.Recipe
import com.devbea.lotuskmm.domain.util.Queue

data class RecipeListState(
    val isLoading: Boolean = false,
    val loaderPosition: Float = ScreenPosition.TOP.value,
    val page: Int = 1,
    val query: String = "",
    val selectedCategory: FoodCategory? = null,
    val recipes: List<Recipe> = listOf(),
    val queue: Queue<GenericMessageInfo> = Queue(mutableListOf())
)