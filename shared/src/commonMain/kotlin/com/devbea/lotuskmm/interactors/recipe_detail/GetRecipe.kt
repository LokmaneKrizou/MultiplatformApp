package com.devbea.lotuskmm.interactors.recipe_detail

import com.devbea.lotuskmm.datasource.cache.RecipeCache
import com.devbea.lotuskmm.datasource.network.RecipeService
import com.devbea.lotuskmm.domain.model.GenericMessageInfo
import com.devbea.lotuskmm.domain.model.Recipe
import com.devbea.lotuskmm.domain.model.UIComponentType
import com.devbea.lotuskmm.domain.util.CommonFlow
import com.devbea.lotuskmm.domain.util.DataState
import com.devbea.lotuskmm.domain.util.asCommonFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetRecipe(
    private val recipeService: RecipeService,
    private val recipeCache: RecipeCache
) {

    fun execute(recipeId: Int): CommonFlow<DataState<Recipe>> = flow {
        emit(DataState.loading())
        try {
            val cacheResult = recipeCache.get(recipeId)
            if (cacheResult == null) {
                val recipe = recipeService.get(id = recipeId)
                recipeCache.insert(recipe)
            }
            emit(DataState.data(data = recipeCache.get(recipeId)))
        } catch (e: Exception) {
            emit(
                DataState.error(
                    message = GenericMessageInfo.Builder()
                        .id("GetRecipe.Error")
                        .title("Error")
                        .uiComponentType(UIComponentType.Dialog)
                        .description(e.message ?: "unknown error")
                )
            )
        }
    }.asCommonFlow()
}