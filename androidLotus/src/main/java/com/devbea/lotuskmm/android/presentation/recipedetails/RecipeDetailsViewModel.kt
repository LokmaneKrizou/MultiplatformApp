package com.devbea.lotuskmm.android.presentation.recipedetails

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devbea.lotuskmm.android.presentation.navigation.RouteParam.RECIPE_ID
import com.devbea.lotuskmm.domain.model.Recipe
import com.devbea.lotuskmm.domain.util.DataState
import com.devbea.lotuskmm.interactors.recipe_detail.GetRecipe
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

    val recipe: MutableState<DataState<Recipe>?> = mutableStateOf(null)

    init {
        savedStateHandle.get<Int?>(RECIPE_ID)?.let { recipeId ->
            getRecipeById(recipeId)
        }
    }

    fun getRecipeById(id: Int) {
        getRecipeUseCase.execute(id).onEach { dataState ->
            recipe.value = dataState
            println("RecipeDetail: ${dataState.isLoading}")
            dataState.data?.let { recipe -> println("RecipeDetail: $recipe") }
            dataState.message?.let { message -> println("RecipeDetail: $message") }
        }.launchIn(viewModelScope)
    }
}