package com.devbea.lotuskmm.android.presentation.recipedetails

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devbea.lotuskmm.android.presentation.navigation.RouteParam.RECIPE_ID
import com.devbea.lotuskmm.android.presentation.util.invalidEventErrorDialog
import com.devbea.lotuskmm.domain.model.GenericMessageInfo
import com.devbea.lotuskmm.domain.util.GenericInfoUtil
import com.devbea.lotuskmm.domain.util.Queue
import com.devbea.lotuskmm.interactors.recipe_detail.GetRecipe
import com.devbea.lotuskmm.presentation.recipe_detail.RecipeDetailEvents
import com.devbea.lotuskmm.presentation.recipe_detail.RecipeDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@OptIn(ExperimentalStdlibApi::class)
@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getRecipeUseCase: GetRecipe
) : ViewModel() {

    val state: MutableState<RecipeDetailState> = mutableStateOf(RecipeDetailState())

    init {
        savedStateHandle.get<Int?>(RECIPE_ID)?.let { recipeId ->
            onTriggerEvent(RecipeDetailEvents.LoadRecipe(recipeId))

        }
    }

    fun onTriggerEvent(event: RecipeDetailEvents) {
        when (event) {
            is RecipeDetailEvents.LoadRecipe -> {
                getRecipeById(event.id)
            }
            RecipeDetailEvents.OnRemoveHeadMessageFromQueue -> {
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

    private fun getRecipeById(id: Int) {
        getRecipeUseCase.execute(id).onEach { dataState ->
            state.value = state.value.copy(isLoading = dataState.isLoading)
            dataState.data?.let { recipe ->
                state.value = state.value.copy(recipe = recipe)
            }
            dataState.message?.let { message ->
                appendToMessageQueue(message)
            }
        }.launchIn(viewModelScope)
    }
}