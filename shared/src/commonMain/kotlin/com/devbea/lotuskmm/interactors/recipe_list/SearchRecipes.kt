package com.devbea.lotuskmm.interactors.recipe_list

import com.devbea.lotuskmm.datasource.cache.RecipeCache
import com.devbea.lotuskmm.datasource.network.RecipeService
import com.devbea.lotuskmm.domain.model.Recipe
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
            val recipes = recipeService.search(page, query)
            recipeCache.insert(recipes)
            val cacheResult = if (query.isBlank()) {
                recipeCache.getAll(page)
            } else {
                recipeCache.search(query, page)
            }
            emit(DataState.data(data = cacheResult))


        } catch (e: Exception) {
            emit(DataState.error(message = e.message ?: "uknonw error"))
        }


    }

}