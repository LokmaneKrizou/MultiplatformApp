package com.devbea.lotuskmm.presentation.recipe_list

sealed class RecipeListEvents {
    object LoadRecipes : RecipeListEvents()
    object NextPage : RecipeListEvents()
    object NewSearch : RecipeListEvents()
    class OnUpdateQuery(val query: String) : RecipeListEvents()
    class OnSelectCategory(val category: FoodCategory) : RecipeListEvents()
    object OnRemoveHeadMessageFromQueue : RecipeListEvents()
}