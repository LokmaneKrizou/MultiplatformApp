package com.devbea.lotuskmm.datasource.cache

import com.devbea.lotuskmm.datasource.network.RecipeServiceImpl.Companion.PAGINATION_PAGE_SIZE
import com.devbea.lotuskmm.datasource.network.dateTimeUtil
import com.devbea.lotuskmm.domain.model.Recipe
import com.devbea.lotuskmm.domain.util.DatetimeUtil

class RecipeCacheImpl(
    private val recipeDatabase: RecipeDatabase,
    private val datetimeUtil: DatetimeUtil
) : RecipeCache {

    private val queries: RecipeDbQueries = recipeDatabase.recipeDbQueries
    override fun insert(recipe: Recipe) {
        recipe.apply {
            queries.insertRecipe(
                id = id.toLong(),
                title = title,
                publisher = publisher,
                ingredients = ingredients.convertIngredientsToString(),
                rating = rating.toLong(),
                source_url = sourceUrl,
                featured_image = featuredImage,
                date_added = dateTimeUtil.toEpochMilliseconds(dateAdded),
                date_updated = datetimeUtil.toEpochMilliseconds(dateUpdated)
            )

        }

    }

    override fun insert(recipes: List<Recipe>) {
        recipes.forEach { insert(it) }
    }

    override fun search(query: String, page: Int): List<Recipe> {
        return queries.searchRecipes(
            query = query,
            pageSize = PAGINATION_PAGE_SIZE.toLong(),
            offset = ((page - 1) * PAGINATION_PAGE_SIZE).toLong()
        ).executeAsList().toRecipeList()
    }

    override fun getAll(page: Int): List<Recipe> {
        return queries.getAllRecipes(
            pageSize = PAGINATION_PAGE_SIZE.toLong(),
            offset = ((page - 1) * PAGINATION_PAGE_SIZE).toLong(),
        ).executeAsList().toRecipeList()
    }

    override fun get(recipeId: Int): Recipe? {
        return try {
            queries.getRecipeById(recipeId.toLong()).executeAsOne().toRecipe()
        } catch (e: Exception) {
            null
        }
    }
}