package com.devbea.lotuskmm.datasource.cache

import com.devbea.lotuskmm.domain.model.Recipe

interface RecipeCache {

    fun insert(recipe: Recipe)

    fun insert(recipes: List<Recipe>)

    fun search(query: String, page: Int): List<Recipe>

    fun getAll(page: Int): List<Recipe>

    fun get(recipeId: Int): Recipe?
}