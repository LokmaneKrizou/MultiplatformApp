package com.devbea.lotuskmm.android.presentation.recipes

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.tooling.preview.Preview
import com.devbea.lotuskmm.android.presentation.recipes.components.RecipeList
import com.devbea.lotuskmm.android.presentation.recipes.components.SearchAppBar
import com.devbea.lotuskmm.android.presentation.theme.AppTheme
import com.devbea.lotuskmm.datasource.network.model.RecipeDto
import com.devbea.lotuskmm.datasource.network.toRecipe
import com.devbea.lotuskmm.presentation.recipe_list.FoodCategoryUtil
import com.devbea.lotuskmm.presentation.recipe_list.RecipeListEvents
import com.devbea.lotuskmm.presentation.recipe_list.RecipeListEvents.OnRemoveHeadMessageFromQueue
import com.devbea.lotuskmm.presentation.recipe_list.RecipeListState

@ExperimentalMaterialApi
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RecipeListScreen(
    state: RecipeListState,
    onTriggerEvent: (RecipeListEvents) -> Unit,
    onSelectedRecipe: ((Int) -> Unit)
) {
    AppTheme(
        displayProgressBar = state.isLoading,
        dialogQueue = state.queue,
        positionProgressBar = state.loaderPosition,
        onRemoveHeadFromQueue = { onTriggerEvent(OnRemoveHeadMessageFromQueue) }
    ) {
        val foodCategories = remember { FoodCategoryUtil().getAllFoodCategories() }
        Scaffold(topBar = {
            SearchAppBar(query = state.query,
                selectedCategory = state.selectedCategory,
                categories = foodCategories,
                onQueryChange = { onTriggerEvent(RecipeListEvents.OnUpdateQuery(it)) },
                onExecuteSearch = {
                    onTriggerEvent(RecipeListEvents.NewSearch)
                },
                onSelectedCategoryChanged = {
                    onTriggerEvent(RecipeListEvents.OnSelectCategory(it))
                }
            )
        }) {
            RecipeList(
                isLoading = state.isLoading,
                recipes = state.recipes,
                page = state.page,
                onTriggerNextPage = { onTriggerEvent(RecipeListEvents.NextPage) },
                onSelectedRecipe = onSelectedRecipe
            )

        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
private fun RecipeListScreenPreview() {
    RecipeListScreen(
        state = RecipeListState(
            recipes = listOf(
                RecipeDto(
                    1,
                    "title",
                    "me",
                    "s",
                    5,
                    "",
                    listOf(),
                    123,
                    123
                ).toRecipe()
            )
        ),
        {

        },
        {

        })
}