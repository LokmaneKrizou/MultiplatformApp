package com.devbea.lotuskmm.presentation.recipe_detail

import com.devbea.lotuskmm.domain.model.GenericMessageInfo
import com.devbea.lotuskmm.domain.model.Recipe
import com.devbea.lotuskmm.domain.util.Queue

actual data class RecipeDetailState(
    val isLoading: Boolean = false,
    val recipe: Recipe? = null,
    val queue: Queue<GenericMessageInfo> = Queue(mutableListOf())
)