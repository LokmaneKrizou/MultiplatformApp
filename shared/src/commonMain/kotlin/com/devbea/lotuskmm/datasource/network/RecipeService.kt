package com.devbea.lotuskmm.datasource.network

import com.devbea.lotuskmm.domain.model.Recipe

interface RecipeService {

    suspend fun search(page: Int, query: String): List<Recipe>

    suspend fun get(id: Int): Recipe
}