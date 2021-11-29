package com.devbea.lotuskmm.android.presentation.recipes.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devbea.lotuskmm.android.presentation.components.RecipeImage
import com.devbea.lotuskmm.domain.model.Recipe

@Composable
fun RecipeCard(recipe: Recipe, onClick: () -> Unit) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(vertical = 6.dp, horizontal = 16.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 2.dp
    ) {
        Column {
            RecipeImage(url = recipe.featuredImage)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 12.dp)
            ) {
                Text(
                    text = recipe.title,
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .wrapContentWidth(Alignment.Start),
                    style = MaterialTheme.typography.h3
                )
                Text(
                    text = recipe.rating.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.End).align(Alignment.CenterVertically),
                    style = MaterialTheme.typography.h5
                )


            }

        }
    }
}