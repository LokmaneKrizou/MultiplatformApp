package com.devbea.lotuskmm.datasource.network

import com.devbea.lotuskmm.datasource.network.model.RecipeDto
import com.devbea.lotuskmm.domain.model.Recipe
import com.devbea.lotuskmm.domain.util.DatetimeUtil
import io.ktor.client.*

expect class KtorClientFactory() {
    fun build(): HttpClient
}

val dateTimeUtil = DatetimeUtil()
fun RecipeDto.toRecipe(): Recipe = Recipe(
    id = pk,
    title = title,
    publisher = publisher,
    rating = rating,
    featuredImage = featuredImage,
    sourceUrl = sourceUrl,
    ingredients = ingredients,
    dateAdded = dateTimeUtil.toLocalDate(longDateAdded.toDouble()),
    dateUpdated = dateTimeUtil.toLocalDate(longDateUpdated.toDouble())
)

fun List<RecipeDto>.toRecipeList(): List<Recipe> = map { it.toRecipe() }