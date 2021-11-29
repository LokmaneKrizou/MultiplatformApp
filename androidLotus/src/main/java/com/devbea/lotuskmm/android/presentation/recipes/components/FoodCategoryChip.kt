package com.devbea.lotuskmm.android.presentation.recipes.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FoodCategoryChip(
    category: String,
    isSelected: Boolean = false,
    onCategoryChanged: (String) -> Unit
) {
    androidx.compose.material.Surface(
        modifier = Modifier.padding(end = 8.dp),
        elevation = 2.dp,
        shape = MaterialTheme.shapes.medium,
        color = if (isSelected) Color.LightGray else MaterialTheme.colors.secondary
    ) {
        Row(modifier = Modifier.clickable { onCategoryChanged(category) }) {
            Text(
                text = category,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSecondary,
                modifier = Modifier.padding(8.dp)
            )
        }

    }

}