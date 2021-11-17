package com.devbea.lotuskmm.android.presentation.recipedetails

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.devbea.lotuskmm.android.presentation.navigation.RouteParam.RECIPE_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(private val savedStateHandle: SavedStateHandle) :
    ViewModel() {

    val recipeId: MutableState<Int?> = mutableStateOf(null)

    init {
        savedStateHandle.get<Int?>(RECIPE_ID)?.let { recipeId ->
            this.recipeId.value = recipeId
        }

    }
}