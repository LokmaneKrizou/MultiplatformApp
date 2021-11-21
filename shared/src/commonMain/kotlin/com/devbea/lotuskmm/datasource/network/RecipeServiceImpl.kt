package com.devbea.lotuskmm.datasource.network

import com.devbea.lotuskmm.datasource.network.model.RecipeDto
import com.devbea.lotuskmm.datasource.network.model.RecipeSearchResponse
import com.devbea.lotuskmm.domain.model.Recipe
import io.ktor.client.*
import io.ktor.client.request.*

class RecipeServiceImpl(
    private val httpClient: HttpClient,
    private val baseUrl: String?= BASE_URL
) : RecipeService {
    override suspend fun search(page: Int, query: String): List<Recipe> {
      return  httpClient.get<RecipeSearchResponse> {
            url("$baseUrl/search?page=$page&query=$query")
            header(AUTHORIZATION, TOKEN)
        }.results.toRecipeList()
    }

    override suspend fun get(id: Int): Recipe {
        return  httpClient.get<RecipeDto> {
            url("$baseUrl/get?id=$id")
            header(AUTHORIZATION, TOKEN)
        }.toRecipe()
    }

    companion object{
        const val TOKEN = "Token 9c8b06d329136da358c2d00e76946b0111ce2c48"
        const val BASE_URL = "https://food2fork.ca/api/recipe"
        const val AUTHORIZATION = "Authorization"
        const val PAGINATION_PAGE_SIZE=30

    }
}