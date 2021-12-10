package com.devbea.lotuskmm.interactors.recipe_list

import com.devbea.lotuskmm.datasource.cache.RecipeCache
import com.devbea.lotuskmm.datasource.network.RecipeService
import com.devbea.lotuskmm.domain.model.GenericMessageInfo
import com.devbea.lotuskmm.domain.model.PositiveAction
import com.devbea.lotuskmm.domain.model.Recipe
import com.devbea.lotuskmm.domain.model.UIComponentType
import com.devbea.lotuskmm.domain.util.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchRecipes(
    private val recipeService: RecipeService,
    private val recipeCache: RecipeCache
) {
    fun execute(page: Int, query: String): Flow<DataState<List<Recipe>>> = flow {
        emit(DataState.loading())
        try {
            delay(500)
            if (query == "error") {
                throw Exception("Forcing an error..., Search Failed :(")
            }
            val recipes = recipeService.search(page, query)
            recipeCache.insert(recipes)
            val cacheResult = if (query.isBlank()) {
                recipeCache.getAll(page)
            } else {
                recipeCache.search(query, page)
            }
            emit(DataState.data(data = cacheResult))

        } catch (e: Exception) {
            emit(
                DataState.error(
                    message = GenericMessageInfo.Builder()
                        .id("SearchRecipes.Error")
                        .title("Error")
                        .uiComponentType(UIComponentType.Dialog)
                        .description(e.message ?: "unknown error")
                        .positive(PositiveAction { })
                )
            )
        }


    }

}