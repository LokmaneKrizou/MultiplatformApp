package com.devbea.lotuskmm.android.presentation.recipes

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devbea.lotuskmm.android.presentation.util.invalidEventErrorDialog
import com.devbea.lotuskmm.domain.model.GenericMessageInfo
import com.devbea.lotuskmm.domain.model.Recipe
import com.devbea.lotuskmm.domain.util.GenericInfoUtil
import com.devbea.lotuskmm.domain.util.Queue
import com.devbea.lotuskmm.interactors.recipe_list.SearchRecipes
import com.devbea.lotuskmm.presentation.recipe_list.FoodCategory
import com.devbea.lotuskmm.presentation.recipe_list.RecipeListEvents
import com.devbea.lotuskmm.presentation.recipe_list.RecipeListState
import com.devbea.lotuskmm.presentation.recipe_list.ScreenPosition
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(private val searchRecipes: SearchRecipes) :
    ViewModel() {
    val state: MutableState<RecipeListState> = mutableStateOf(RecipeListState())

    init {
        onTriggerEvent(RecipeListEvents.LoadRecipes)
    }

    fun onTriggerEvent(event: RecipeListEvents) {
        when (event) {
            RecipeListEvents.LoadRecipes -> {
                loadRecipes()
            }
            RecipeListEvents.NextPage -> {
                nextPage()
            }
            RecipeListEvents.NewSearch -> {
                newSearch()
            }
            is RecipeListEvents.OnUpdateQuery -> {
                state.value = state.value.copy(query = event.query, selectedCategory = null)
            }
            is RecipeListEvents.OnSelectCategory -> {
                onSelectCategory(event.category)
            }
            RecipeListEvents.OnRemoveHeadMessageFromQueue -> {
                removeHeadMessage()
            }
            else -> {
                appendToMessageQueue(invalidEventErrorDialog())
            }
        }
    }

    private fun removeHeadMessage() {
        try {
            val queue = state.value.queue
            queue.remove()
            state.value = state.value.copy(queue = Queue(mutableListOf()))
            state.value = state.value.copy(queue = queue)
        } catch (e: Exception) {
            //Nothing to remove, empty queue
        }
    }

    private fun onSelectCategory(category: FoodCategory) {
        state.value = state.value.copy(selectedCategory = category, query = category.value)
        newSearch()
    }

    private fun newSearch() {
        state.value = state.value.copy(page = 1, recipes = listOf())
        loadRecipes()
    }

    private fun appendToMessageQueue(errorMessage: GenericMessageInfo.Builder) {
        val queue = state.value.queue

        if (!GenericInfoUtil().doesMessageAlreadyExistInQueue(
                queue = queue,
                messageInfo = errorMessage.build()
            )
        ) {
            queue.add(errorMessage.build())
            state.value = state.value.copy(queue = queue)
        }
    }

    private fun nextPage() {
        state.value = state.value.copy(
            page = state.value.page + 1,
            loaderPosition = ScreenPosition.BOTTOM.value
        )
        loadRecipes()
    }

    private fun loadRecipes() {
        searchRecipes.execute(state.value.page, state.value.query)
            .collectCommon(coroutineScope = viewModelScope) { dataState ->
                state.value = state.value.copy(isLoading = dataState.isLoading)
                dataState.data?.let { appendRecipes(it) }
                dataState.message?.let { message -> appendToMessageQueue(message) }
            }
    }

    private fun appendRecipes(recipes: List<Recipe>) {
        val current = ArrayList(state.value.recipes)
        current.addAll(recipes)
        state.value = state.value.copy(recipes = current)
    }
}