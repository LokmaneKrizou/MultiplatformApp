package com.devbea.lotuskmm.di

import com.devbea.lotuskmm.datasource.network.KtorClientFactory
import com.devbea.lotuskmm.datasource.network.RecipeService
import com.devbea.lotuskmm.datasource.network.RecipeServiceImpl

class NetworkModule {

    val recipeService: RecipeService by lazy {
        RecipeServiceImpl(httpClient = KtorClientFactory().build())
    }

}