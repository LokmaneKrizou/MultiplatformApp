package com.devbea.lotuskmm.android.presentation.recipes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun ShimmerRecipeCardItem(
    colors: List<Color>,
    xShimmer: Float,
    yShimmer: Float,
    cardHeight: Dp,
    gradientWidth: Float,
    padding: Dp,
    numberOfLines: Int = 1
) {
    val brush =
        Brush.linearGradient(
            colors = colors,
            start = Offset(xShimmer - gradientWidth, yShimmer - gradientWidth),
            end = Offset(xShimmer, yShimmer)
        )
    Column(
        modifier = Modifier.padding(padding)
    ) {
        Surface(shape = MaterialTheme.shapes.small) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(cardHeight)
                    .background(brush = brush)

            )
        }
        for (item in 1..numberOfLines) {
            Spacer(modifier = Modifier.height(8.dp))
            Surface(shape = MaterialTheme.shapes.small) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(cardHeight / 10)
                        .background(brush = brush)
                )
            }
        }
    }
}