package com.devbea.lotuskmm.android.presentation.recipes

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devbea.lotuskmm.domain.model.Recipe
import com.devbea.lotuskmm.domain.util.DataState
import com.devbea.lotuskmm.interactors.recipe_list.SearchRecipes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(private val searchRecipes: SearchRecipes) :
    ViewModel() {
    val recipes: MutableState<DataState<List<Recipe>>?> = mutableStateOf(null)

    init {
        loadRecipes()
    }

    private fun loadRecipes() {
        searchRecipes.execute(1, "chicken").onEach { dataState ->
            this.recipes.value = dataState

            println("RecipeList: ${dataState.isLoading}")

            dataState.data?.let { recipes -> println("RecipeList: $recipes") }

            dataState.message?.let { message -> println("RecipeList: $message") }

        }.launchIn(viewModelScope)
    }

}