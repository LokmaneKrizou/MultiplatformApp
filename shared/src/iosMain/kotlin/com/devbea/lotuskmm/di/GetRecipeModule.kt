package com.devbea.lotuskmm.di

import com.devbea.lotuskmm.interactors.recipe_detail.GetRecipe

class GetRecipeModule(
    private val networkModule: NetworkModule,
    private val cacheModule: CacheModule
) {
    val getRecipe: GetRecipe by lazy {
        GetRecipe(
            recipeCache = cacheModule.recipeCache,
            recipeService = networkModule.recipeService
        )
    }
}