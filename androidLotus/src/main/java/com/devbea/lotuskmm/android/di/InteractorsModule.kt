package com.devbea.lotuskmm.android.di

import com.devbea.lotuskmm.datasource.cache.RecipeCache
import com.devbea.lotuskmm.datasource.network.RecipeService
import com.devbea.lotuskmm.interactors.recipe_detail.GetRecipe
import com.devbea.lotuskmm.interactors.recipe_list.SearchRecipes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InteractorsModule {

    @Singleton
    @Provides
    fun provideSearchRecipesUseCase(
        recipeService: RecipeService,
        recipeCache: RecipeCache
    ): SearchRecipes =
        SearchRecipes(recipeService, recipeCache)

    @Singleton
    @Provides
    fun provideGetRecipeUseCase(
        recipeService: RecipeService,
        recipeCache: RecipeCache
    ): GetRecipe =
        GetRecipe(recipeService, recipeCache)
}