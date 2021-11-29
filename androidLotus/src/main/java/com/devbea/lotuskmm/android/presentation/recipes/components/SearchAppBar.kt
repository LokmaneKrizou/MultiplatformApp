package com.devbea.lotuskmm.android.presentation.recipes.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.devbea.lotuskmm.presentation.recipe_list.FoodCategory
import com.devbea.lotuskmm.presentation.recipe_list.FoodCategoryUtil

@ExperimentalComposeUiApi
@Composable
fun SearchAppBar(
    query: String,
    selectedCategory: FoodCategory? = null,
    categories: List<FoodCategory>,
    onQueryChange: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    onSelectedCategoryChanged: (FoodCategory) -> Unit

) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.primary,
        elevation = 2.dp
    ) {
        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = query,
                    onValueChange = onQueryChange,
                    label = { Text("Search...") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            onExecuteSearch()
                            keyboardController?.hide()
                        }
                    ),
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search icon") },
                    textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface)
                )

            }
            LazyRow(modifier = Modifier.padding(start = 8.dp, bottom = 8.dp, top = 8.dp)) {
                items(categories) {
                    FoodCategoryChip(
                        category = it.value,
                        isSelected = selectedCategory == it,
                        onCategoryChanged = { category ->
                            FoodCategoryUtil().getFoodCategory(category)?.let { newCategory ->
                                onSelectedCategoryChanged(newCategory)
                            }
                        }
                    )
                }
            }
        }
    }
}