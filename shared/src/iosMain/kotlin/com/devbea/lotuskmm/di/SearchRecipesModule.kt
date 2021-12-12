package com.devbea.lotuskmm.di

import com.devbea.lotuskmm.interactors.recipe_list.SearchRecipes

class SearchRecipesModule(
    private val networkModule: NetworkModule,
    private val cacheModule: CacheModule
) {
    val searchRecipes: SearchRecipes by lazy {
        SearchRecipes(
            recipeCache = cacheModule.recipeCache,
            recipeService = networkModule.recipeService
        )
    }
}